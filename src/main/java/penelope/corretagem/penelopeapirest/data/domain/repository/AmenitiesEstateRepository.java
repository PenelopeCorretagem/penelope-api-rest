package penelope.corretagem.penelopeapirest.data.domain.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import penelope.corretagem.penelopeapirest.data.domain.entity.AmenitiesEstateEntity;

public interface
AmenitiesEstateRepository extends JpaRepository<AmenitiesEstateEntity, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO diferencial_empreendimento (fk_empreendimento, fk_diferencial) " +
            "VALUES (:fkEstate, :fkFeature)", nativeQuery = true)
    void insertFeatureNative(@Param("fkEstate") Long fkEstate,
                             @Param("fkFeature") Long fkFeature);

    @Query(value = "SELECT LAST_INSERT_ID()", nativeQuery = true)
    Long getLastInsertId();
}
