package penelope.corretagem.penelopeapirest.application.useCase.advertisement;

import penelope.corretagem.penelopeapirest.application.dto.AdvertisementUpdateRequest;
import penelope.corretagem.penelopeapirest.application.dto.EstateCreateRequest;
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

public class UpdateAdvertisementUseCase {

    private final IAdvertisementRepository advertisementRepository;
    private final IEstateEventPublisherGateway estateEventPublisher;
    private final IUserRepository userRepository;

    public UpdateAdvertisementUseCase(
            IAdvertisementRepository advertisementRepository,
            IEstateEventPublisherGateway estateEventPublisher,
            IUserRepository userRepository
    ) {
        this.advertisementRepository = advertisementRepository;
        this.estateEventPublisher = estateEventPublisher;
        this.userRepository = userRepository;
    }

    public Advertisement execute(Long advertisementId, AdvertisementUpdateRequest request) {

        if (advertisementId == null || advertisementId <= 0) {
            throw new DomainValidationException("ID do anúncio inválido");
        }

        if (request == null) {
            throw new DomainValidationException("Requisição de atualização do anúncio é obrigatória");
        }

        Advertisement advertisement = advertisementRepository.findById(advertisementId)
                .orElseThrow(() -> new ResourceNotFoundException("Anúncio não encontrado"));

        Estate currentEstate = advertisement.getEstate();
        EstateCreateRequest estateReq = request.estate();
        boolean shouldUpdateEventType = false;

        if (estateReq != null) {
            if (estateReq.address() == null) {
                throw new DomainValidationException("Endereço do imóvel é obrigatório para atualização");
            }

            String normalizedTitle = estateReq.title() != null ? estateReq.title().trim() : null;
            if (normalizedTitle == null || normalizedTitle.isBlank()) {
                throw new DomainValidationException("Título do anúncio é obrigatório");
            }

            if (advertisementRepository.existsByEstateTitleAndIdNot(normalizedTitle, advertisementId)) {
                throw new DomainValidationException("Já existe anúncio com este título");
            }

            shouldUpdateEventType = hasEventTypeRelevantChanges(currentEstate, estateReq);

            String cleanZipCode = estateReq.address().zipCode() != null
                    ? estateReq.address().zipCode().replaceAll("[^0-9]", "") : null;

            var newAddress = Address.createNew(
                    estateReq.address().street(), estateReq.address().number(), estateReq.address().neighborhood(),
                    estateReq.address().city(), estateReq.address().uf(), cleanZipCode,
                    estateReq.address().complement(), estateReq.address().region()
            );

            Set<AmenitiesEstate> newAmenities = estateReq.amenitiesIds() != null ?
                    estateReq.amenitiesIds().stream()
                            .map(id ->
                                    AmenitiesEstate.createNew(
                                            null, null, Amenities.restore(id, null, null, null)
                                    )
                            ).collect(Collectors.toSet()) : new java.util.HashSet<>();

            Set<ImageEstate> newImages = new java.util.HashSet<>();
            if (estateReq.images() != null) {
                for (var imgReq : estateReq.images()) {
                    Long typeId = 2L;
                    if ("CAPA".equalsIgnoreCase(imgReq.type())) {
                        typeId = 1L;
                    } else if ("PLANTA".equalsIgnoreCase(imgReq.type())) {
                        typeId = 3L;
                    } else if ("VIDEO".equalsIgnoreCase(imgReq.type())) {
                        typeId = 4L;
                    }
                    newImages.add(ImageEstate.createNew(null, ImageEstateType.restore(typeId, null, null), imgReq.url()));
                }
            }

            currentEstate.updateAllDetails(
                    normalizedTitle, estateReq.description(), estateReq.area(), estateReq.numberOfRooms(),
                    parseEstateType(estateReq.type()), newAddress, newImages, newAmenities
            );
        }

        if (request.responsibleId() != null) {
            User responsible = userRepository.findById(request.responsibleId())
                    .orElseThrow(() -> new ResourceNotFoundException("Novo responsável não encontrado"));
            advertisement.updateResponsible(responsible);
        }

        if (request.featured() != null) {
            advertisement.setEmphasis(request.featured());
        }

        advertisement.updateInfo(request.active(), request.endDate());

        var savedAdvertisement = advertisementRepository.update(advertisement);
        if (shouldUpdateEventType) {
            estateEventPublisher.publishEstateUpdated(savedAdvertisement.getEstate(), savedAdvertisement.getId());
        }
        return savedAdvertisement;
    }

    private boolean hasEventTypeRelevantChanges(Estate estate, EstateCreateRequest req) {
        return !estate.getTitle().equals(req.title());
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