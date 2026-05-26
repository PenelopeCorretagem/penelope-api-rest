package penelope.corretagem.penelopeapirest.core.advertisement.repository;

import penelope.corretagem.penelopeapirest.core.advertisement.Advertisement;
import penelope.corretagem.penelopeapirest.core.advertisement.AdvertisementFilter;
import java.util.List;
import java.util.Optional;

public interface IAdvertisementRepository {

    // Escritas
    Advertisement save(Advertisement advertisement);
    void updateStatus(Long id, Boolean active);
    Advertisement update(Advertisement advertisement);
    void deleteById(Long id);
    boolean existsByEstateTitle(String title);
    boolean existsByEstateTitleAndIdNot(String title, Long advertisementId);

    // Leituras
    Optional<Advertisement> findById(Long id);
    Optional<Advertisement> findLatest(boolean includeInactive);
    Advertisement findByEstateId(Long estateId, boolean includeInactive);
    List<Advertisement> findAll(AdvertisementFilter filter);
}
