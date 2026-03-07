package penelope.corretagem.penelopeapirest.core.advertisement.repository;

import penelope.corretagem.penelopeapirest.core.advertisement.Advertisement;
import java.util.List;
import java.util.Optional;

public interface AdvertisementRepository {

    // Escritas
    Advertisement save(Advertisement advertisement);
    void deactivateById(Long id);

    // Leituras
    Optional<Advertisement> findById(Long id);
    Optional<Advertisement> findTopByOrderByCreatedAtDesc();
    Advertisement findByEstateId(Long estateId);
    List<Advertisement> findExpiredActiveAdvertisements();
}