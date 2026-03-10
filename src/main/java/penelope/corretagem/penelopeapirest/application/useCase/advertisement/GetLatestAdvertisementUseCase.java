package penelope.corretagem.penelopeapirest.application.useCase.advertisement;

import org.springframework.stereotype.Service;
import penelope.corretagem.penelopeapirest.core.advertisement.repository.IAdvertisementRepository;
import penelope.corretagem.penelopeapirest.application.dto.AdvertisementResponse;

@Service
public class GetLatestAdvertisementUseCase {

    private final IAdvertisementRepository repository;

    public GetLatestAdvertisementUseCase(IAdvertisementRepository repository) {
        this.repository = repository;
    }

    public AdvertisementResponse execute() {
        return repository.findTopByOrderByCreatedAtDesc()
                .map(AdvertisementResponse::fromDomain)
                .orElse(null);
    }
}