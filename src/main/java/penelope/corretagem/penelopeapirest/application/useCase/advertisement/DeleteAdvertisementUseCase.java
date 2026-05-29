package penelope.corretagem.penelopeapirest.application.useCase.advertisement;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import penelope.corretagem.penelopeapirest.config.CacheNames;
import penelope.corretagem.penelopeapirest.core.advertisement.repository.IAdvertisementRepository;

public class DeleteAdvertisementUseCase {

    private final IAdvertisementRepository advertisementRepository;

    public DeleteAdvertisementUseCase(IAdvertisementRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
    }

    @Caching(evict = {
            @CacheEvict(value = CacheNames.ADVERTISEMENTS, allEntries = true),
            @CacheEvict(value = CacheNames.ADVERTISEMENT_LATEST, key = "'latest'"),
            @CacheEvict(value = CacheNames.ADVERTISEMENT_BY_ESTATE, allEntries = true)
    })
    public void execute(Long id) {
        advertisementRepository.deleteById(id);
    }
}
