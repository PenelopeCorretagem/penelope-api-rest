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
import penelope.corretagem.penelopeapirest.core.gateway.IEventTypeGateway;
import penelope.corretagem.penelopeapirest.core.exception.ResourceNotFoundException;
import penelope.corretagem.penelopeapirest.core.user.User;
import penelope.corretagem.penelopeapirest.core.user.repository.IUserRepository;

import java.util.Set;
import java.util.stream.Collectors;

public class CreateAdvertisementUseCase {

    private final IAdvertisementRepository advertisementRepository;
    private final IUserRepository userRepository;
    private final IEventTypeGateway eventTypeGateway;

    public CreateAdvertisementUseCase(
            IAdvertisementRepository advertisementRepository,
            IUserRepository userRepository, IEventTypeGateway eventTypeGateway
    ) {
        this.advertisementRepository = advertisementRepository;
        this.userRepository = userRepository;
        this.eventTypeGateway = eventTypeGateway;
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

        Address standAddress = null;
        if (estateRequest.standAddress() != null) {
            standAddress = Address.createNew(
                    estateRequest.standAddress().street(),
                    estateRequest.standAddress().number(),
                    estateRequest.standAddress().neighborhood(),
                    estateRequest.standAddress().city(),
                    estateRequest.standAddress().uf(),
                    cleanZipCode,
                    estateRequest.standAddress().complement(),
                    estateRequest.standAddress().region()
            );
        }

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
                estateRequest.title(),
                estateRequest.description(),
                estateRequest.area(),
                estateRequest.numberOfRooms(),
                parseEstateType(estateRequest.type()),
                address,
                standAddress,
                domainImages,
                domainAmenities
        );

        var eventTypeDomain = eventTypeGateway.generateForEstate(estate);

        var advertisement = Advertisement.createNew(
                estate,
                creator,
                responsible,
                eventTypeDomain,
                request.endDate()
        );

        return advertisementRepository.save(advertisement);
    }

        private Estate.Type parseEstateType(String type) {
                if (type == null || type.isBlank()) {
                        throw new DomainValidationException("Tipo do imóvel é obrigatório");
                }

                try {
                        return Estate.Type.valueOf(type.toUpperCase());
                } catch (IllegalArgumentException ex) {
                        throw new DomainValidationException("Tipo do imóvel inválido: " + type);
                }
        }
}