package penelope.corretagem.penelopeapirest.application.useCase;

import org.springframework.stereotype.Service;
import penelope.corretagem.penelopeapirest.application.dto.EstateCreateRequest;
import penelope.corretagem.penelopeapirest.core.address.Address;
import penelope.corretagem.penelopeapirest.core.advertisement.Advertisement;
import penelope.corretagem.penelopeapirest.core.advertisement.repository.IAdvertisementRepository;
import penelope.corretagem.penelopeapirest.core.estate.Estate;
import penelope.corretagem.penelopeapirest.core.eventType.IEventTypeGateway;
import penelope.corretagem.penelopeapirest.core.user.User;
import penelope.corretagem.penelopeapirest.core.user.repository.IUserRepository;

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

        var estate = Estate.createNew(
                null,
                request.title(),
                request.description(),
                request.area(),
                request.numberOfRooms(),
                Estate.Type.valueOf(request.type()),
                address,
                standAddress,
                null,
                null
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
