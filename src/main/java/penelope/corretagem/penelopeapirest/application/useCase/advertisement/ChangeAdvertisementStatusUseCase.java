package penelope.corretagem.penelopeapirest.application.useCase.advertisement;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import penelope.corretagem.penelopeapirest.config.CacheNames;
import penelope.corretagem.penelopeapirest.core.advertisement.repository.IAdvertisementRepository;

public class ChangeAdvertisementStatusUseCase {

    private final IAdvertisementRepository advertisementRepository;

    public ChangeAdvertisementStatusUseCase(IAdvertisementRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
    }

    @Caching(evict = {
            @CacheEvict(value = CacheNames.ADVERTISEMENTS, allEntries = true),
            @CacheEvict(value = CacheNames.ADVERTISEMENT_LATEST, allEntries = true),
            @CacheEvict(value = CacheNames.ADVERTISEMENT_BY_ESTATE, allEntries = true),
            @CacheEvict(value = CacheNames.ADVERTISEMENT, allEntries = true)
    })
    public void execute(Long advertisementId, Boolean active) {
        advertisementRepository.updateStatus(advertisementId, active);
    }
}