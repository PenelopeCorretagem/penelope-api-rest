package penelope.corretagem.penelopeapirest.application.useCase.advertisement;

import org.springframework.stereotype.Service;
import penelope.corretagem.penelopeapirest.application.dto.EstateCreateRequest;
import penelope.corretagem.penelopeapirest.core.address.Address;
import penelope.corretagem.penelopeapirest.core.advertisement.Advertisement;
import penelope.corretagem.penelopeapirest.core.advertisement.repository.IAdvertisementRepository;
import penelope.corretagem.penelopeapirest.core.amenities.Amenities;
import penelope.corretagem.penelopeapirest.core.amenities.AmenitiesEstate;
import penelope.corretagem.penelopeapirest.core.estate.Estate;
import penelope.corretagem.penelopeapirest.core.estate.ImageEstate;
import penelope.corretagem.penelopeapirest.core.estate.ImageEstateType;
import penelope.corretagem.penelopeapirest.core.eventType.EventType;
import penelope.corretagem.penelopeapirest.core.gateway.IEventTypeGateway;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UpdateAdvertisementUseCase {

    private final IAdvertisementRepository advertisementRepository;
    private final IEventTypeGateway eventTypeGateway;

    public UpdateAdvertisementUseCase(
            IAdvertisementRepository advertisementRepository,
            IEventTypeGateway eventTypeGateway
    ) {
        this.advertisementRepository = advertisementRepository;
        this.eventTypeGateway = eventTypeGateway;
    }

    public Advertisement execute(Long advertisementId, EstateCreateRequest request) {

        Advertisement advertisement = advertisementRepository.findById(advertisementId)
                .orElseThrow(() -> new RuntimeException("Anúncio não encontrado"));

        Estate currentEstate = advertisement.getEstate();

        boolean shouldUpdateEventType = hasEventTypeRelevantChanges(currentEstate, request);

        String cleanZipCode = request.address().zipCode() != null
                ? request.address().zipCode().replaceAll("[^0-9]", "") : null;

        var newAddress = Address.createNew(
                request.address().street(), request.address().number(), request.address().neighborhood(),
                request.address().city(), request.address().uf(), cleanZipCode,
                request.address().complement(), request.address().region()
        );

        Address newStandAddress = null;
        if (request.standAddress() != null) {
            newStandAddress = Address.createNew(
                    request.standAddress().street(), request.standAddress().number(), request.standAddress().neighborhood(),
                    request.standAddress().city(), request.standAddress().uf(), cleanZipCode,
                    request.standAddress().complement(), request.standAddress().region()
            );
        }

        Set<AmenitiesEstate> newAmenities = request.amenitiesIds() != null ?
                request.amenitiesIds().stream()
                        .map(id -> AmenitiesEstate.createNew(null, null, new Amenities(id, null)))
                        .collect(Collectors.toSet()) : new java.util.HashSet<>();

        Set<ImageEstate> newImages = new java.util.HashSet<>();
        if (request.images() != null && request.imageType() != null) {
            for (int i = 0; i < request.images().size(); i++) {
                Long typeId = request.imageType().get(i).longValue();
                newImages.add(ImageEstate.createNew(null, ImageEstateType.restore(typeId, null, null), request.images().get(i)));
            }
        }

        currentEstate.updateAllDetails(
                request.title(), request.description(), request.area(), request.numberOfRooms(),
                Estate.Type.valueOf(request.type()), newAddress, newStandAddress, newImages, newAmenities
        );

        var adRequest = request.advertisementCreateRequest();
        advertisement.updateInfo(adRequest.active(), adRequest.dataFim());

        if (shouldUpdateEventType) {
            EventType newEventType = eventTypeGateway.recreateForEstate(advertisement.getEventType(), currentEstate);

            advertisement.setEventType(newEventType);
        }

        return advertisementRepository.update(advertisement);
    }

    private boolean hasEventTypeRelevantChanges(Estate estate, EstateCreateRequest req) {
        if (!estate.getTitle().equals(req.title())) {
            return true;
        }

        var oldStand = estate.getStandAddress();
        var newStand = req.standAddress();

        if (oldStand == null && newStand == null) return false;
        if (oldStand == null || newStand == null) return true;

        return !oldStand.getStreet().equals(newStand.street()) ||
                !oldStand.getNumber().equals(newStand.number()) ||
                !oldStand.getNeighborhood().equals(newStand.neighborhood()) ||
                !oldStand.getCity().equals(newStand.city()) ||
                !oldStand.getUf().equals(newStand.uf()) ||
                !oldStand.getZipCode().equals(newStand.zipCode()) ||
                !Objects.equals(oldStand.getComplement(), newStand.complement()) ||
                !oldStand.getRegion().equals(newStand.region());
    }
}