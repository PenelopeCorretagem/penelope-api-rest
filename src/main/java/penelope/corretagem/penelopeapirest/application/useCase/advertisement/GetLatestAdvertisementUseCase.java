package penelope.corretagem.penelopeapirest.application.useCase.advertisement;

import org.springframework.cache.annotation.Cacheable;
import penelope.corretagem.penelopeapirest.core.advertisement.repository.IAdvertisementRepository;
import penelope.corretagem.penelopeapirest.application.dto.AdvertisementResponse;
import penelope.corretagem.penelopeapirest.config.CacheNames;

public class GetLatestAdvertisementUseCase {

    private final IAdvertisementRepository repository;

    public GetLatestAdvertisementUseCase(IAdvertisementRepository repository) {
        this.repository = repository;
    }

    @Cacheable(value = CacheNames.ADVERTISEMENT_LATEST, key = "'latest'")
    public AdvertisementResponse execute() {
        return repository.findTopByOrderByCreatedAtDesc()
                .map(AdvertisementResponse::fromDomain)
                .orElse(null);
    }
}