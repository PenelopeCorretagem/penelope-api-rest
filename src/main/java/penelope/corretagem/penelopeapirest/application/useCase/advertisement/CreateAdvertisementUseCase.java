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
import penelope.corretagem.penelopeapirest.core.gateway.IEventTypeGateway;
import penelope.corretagem.penelopeapirest.core.user.User;
import penelope.corretagem.penelopeapirest.core.user.repository.IUserRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
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

    public Advertisement execute(EstateCreateRequest request) {

        var adRequest = request.advertisementCreateRequest();

        User creator = userRepository.findById(adRequest.creator())
                .orElseThrow(() -> new RuntimeException("Usuário criador não encontrado"));

        User responsible = userRepository.findById(adRequest.responsible())
                .orElseThrow(() -> new RuntimeException("Usuário responsável não encontrado"));

        String cleanZipCode = request.address().zipCode() != null
                ? request.address().zipCode().replaceAll("[^0-9]", "")
                : null;

        var address = Address.createNew(
                request.address().street(),
                request.address().number(),
                request.address().neighborhood(),
                request.address().city(),
                request.address().uf(),
                cleanZipCode,
                request.address().complement(),
                request.address().region()
        );

        Address standAddress = null;
        if (request.standAddress() != null) {
            standAddress = Address.createNew(
                    request.standAddress().street(),
                    request.standAddress().number(),
                    request.standAddress().neighborhood(),
                    request.standAddress().city(),
                    request.standAddress().uf(),
                    cleanZipCode,
                    request.standAddress().complement(),
                    request.standAddress().region()
            );
        }

        Set<AmenitiesEstate> domainAmenities = request.amenitiesIds() != null ?
                request.amenitiesIds().stream()
                        .map(id -> {
                            var amenity = new Amenities(id, null);
                            return AmenitiesEstate.createNew(null, null, amenity);
                        })
                        .collect(Collectors.toSet()) : new java.util.HashSet<>();

        Set<ImageEstate> domainImages = new java.util.HashSet<>();
        if (request.images() != null && request.imageType() != null) {

            for (int i = 0; i < request.images().size(); i++) {
                String imageUrl = request.images().get(i);
                Long typeId = request.imageType().get(i).longValue();

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
                request.title(),
                request.description(),
                request.area(),
                request.numberOfRooms(),
                Estate.Type.valueOf(request.type()),
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
                adRequest.dataFim()
        );

        return advertisementRepository.save(advertisement);
    }
}
