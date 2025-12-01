package penelope.corretagem.penelopeapirest.service;

import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import penelope.corretagem.penelopeapirest.data.domain.entity.AdvertisementEntity;
import penelope.corretagem.penelopeapirest.data.domain.repository.AdvertisementRepository;

import java.util.List;

@Component
public class AdvertisementExpirantionScheduler {

    private final AdvertisementRepository advertisementRepository;

    public AdvertisementExpirantionScheduler(AdvertisementRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
    }

    @Scheduled(cron = "0 0/3 * * * *") // roda a cada 1 minuto
    @Transactional
    public void disableExpiredAdvertisements() {

        List<AdvertisementEntity> expiredAds =
                advertisementRepository.findExpiredActiveAdvertisements();

        for (AdvertisementEntity ad : expiredAds) {
            advertisementRepository.deactivateById(ad.getId());
        }

        System.out.println("Scheduler executado. Total de anúncios desativados: " + expiredAds.size());
    }
}
