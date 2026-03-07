package penelope.corretagem.penelopeapirest.service;

import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import penelope.corretagem.penelopeapirest.core.advertisement.Advertisement;
import penelope.corretagem.penelopeapirest.data.domain.repository.AdvertisementRepository;

import java.util.List;

@Component
public class AdvertisementExpirantionScheduler {

    private final AdvertisementRepository advertisementRepository;

    public AdvertisementExpirantionScheduler(AdvertisementRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
    }

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void disableExpiredAdvertisements() {

        List<Advertisement> expiredAds =
                advertisementRepository.findExpiredActiveAdvertisements();

        for (Advertisement ad : expiredAds) {
            advertisementRepository.deactivateById(ad.getId());
        }

        System.out.println("Scheduler executado. Total de anúncios desativados: " + expiredAds.size());
    }
}
