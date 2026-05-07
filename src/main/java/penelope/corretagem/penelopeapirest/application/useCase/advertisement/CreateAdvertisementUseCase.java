package penelope.corretagem.penelopeapirest.application.useCase.advertisement;

import penelope.corretagem.penelopeapirest.application.dto.AdvertisementCreateRequest;
import penelope.corretagem.penelopeapirest.core.address.Address;
import penelope.corretagem.penelopeapirest.core.advertisement.Advertisement;
import penelope.corretagem.penelopeapirest.core.advertisement.repository.IAdvertisementRepository;
import penelope.corretagem.penelopeapirest.core.amenities.Amenities;
import penelope.corretagem.penelopeapirest.core.amenities.AmenitiesEstate;
import penelope.corretagem.penelopeapirest.core.estate.Estate;
import penelope.corretagem.penelopeapirest.core.estate.ImageEstate;
import penelope.corretagem.penelopeapirest.core.estate.ImageEstateType;
import penelope.corretagem.penelopeapirest.core.exception.DomainValidationException;
import penelope.corretagem.penelopeapirest.core.exception.ResourceNotFoundException;
import penelope.corretagem.penelopeapirest.core.gateway.IEstateEventPublisherGateway;
import penelope.corretagem.penelopeapirest.core.user.User;
import penelope.corretagem.penelopeapirest.core.user.repository.IUserRepository;

import java.util.Set;
import java.util.stream.Collectors;

public class CreateAdvertisementUseCase {

    private final IAdvertisementRepository advertisementRepository;
    private final IUserRepository userRepository;
    private final IEstateEventPublisherGateway estateEventPublisher;

    public CreateAdvertisementUseCase(
            IAdvertisementRepository advertisementRepository,
            IUserRepository userRepository,
            IEstateEventPublisherGateway estateEventPublisher
    ) {
        this.advertisementRepository = advertisementRepository;
        this.userRepository = userRepository;
        this.estateEventPublisher = estateEventPublisher;
    }

    public Advertisement execute(AdvertisementCreateRequest request) {

                if (request == null) {
                        throw new DomainValidationException("Requisição de criação do anúncio é obrigatória");
                }

                if (request.creatorId() == null || request.responsibleId() == null) {
                        throw new DomainValidationException("IDs de criador e responsável são obrigatórios");
                }

                if (request.estate() == null || request.estate().address() == null) {
                        throw new DomainValidationException("Dados do imóvel e endereço são obrigatórios");
                }

        var estateRequest = request.estate();

                String normalizedTitle = estateRequest.title() != null ? estateRequest.title().trim() : null;
                if (normalizedTitle == null || normalizedTitle.isBlank()) {
                        throw new DomainValidationException("Título do anúncio é obrigatório");
                }

                if (advertisementRepository.existsByEstateTitle(normalizedTitle)) {
                        throw new DomainValidationException("Já existe anúncio com este título");
                }

        User creator = userRepository.findById(request.creatorId())
                                .orElseThrow(() -> new ResourceNotFoundException("Usuário criador não encontrado"));

        User responsible = userRepository.findById(request.responsibleId())
                                .orElseThrow(() -> new ResourceNotFoundException("Usuário responsável não encontrado"));

        String cleanZipCode = estateRequest.address().zipCode() != null
                ? estateRequest.address().zipCode().replaceAll("[^0-9]", "")
                : null;

        var address = Address.createNew(
                estateRequest.address().street(),
                estateRequest.address().number(),
                estateRequest.address().neighborhood(),
                estateRequest.address().city(),
                estateRequest.address().uf(),
                cleanZipCode,
                estateRequest.address().complement(),
                estateRequest.address().region()
        );

        Set<AmenitiesEstate> domainAmenities = estateRequest.amenitiesIds() != null ?
                estateRequest.amenitiesIds().stream()
                        .map(id -> {
                            var amenity = Amenities.restore(id, null, null, null);
                            return AmenitiesEstate.createNew(null, null, amenity);
                        })
                        .collect(Collectors.toSet()) : new java.util.HashSet<>();

        Set<ImageEstate> domainImages = new java.util.HashSet<>();
        if (estateRequest.images() != null) {
            for (var imgReq : estateRequest.images()) {

                String imageUrl = imgReq.url();
                String typeString = imgReq.type();

                                Long typeId = 2L; // Padrão Galeria
                                if ("CAPA".equalsIgnoreCase(typeString)) {
                                        typeId = 1L;
                                } else if ("PLANTA".equalsIgnoreCase(typeString)) {
                                        typeId = 3L;
                                } else if ("VIDEO".equalsIgnoreCase(typeString)) {
                                        typeId = 4L;
                                }

                var imageTypeDomain = ImageEstateType.restore(typeId, null, null);

                var imageEstate = ImageEstate.createNew(
                        null,
                        imageTypeDomain,
                        imageUrl
                );
                domainImages.add(imageEstate);
            }
        }

        var estate = Estate.createNew(
                null,
                normalizedTitle,
                estateRequest.description(),
                estateRequest.area(),
                estateRequest.numberOfRooms(),
                parseEstateType(estateRequest.type()),
                address,
                domainImages,
                domainAmenities
        );

        var advertisement = Advertisement.createNew(
                estate,
                creator,
                responsible,
                request.endDate()
        );

        var savedAdvertisement = advertisementRepository.save(advertisement);
        estateEventPublisher.publishEstateCreated(savedAdvertisement.getEstate(), savedAdvertisement.getId());
        return savedAdvertisement;
    }

        private Estate.Type parseEstateType(Integer type) {
                if (type == null) {
                        throw new DomainValidationException("Tipo do imóvel é obrigatório");
                }

                try {
                        return Estate.Type.fromCode(type);
                } catch (IllegalArgumentException ex) {
                        throw new DomainValidationException("Tipo do imóvel inválido: " + type);
                }
        }
}