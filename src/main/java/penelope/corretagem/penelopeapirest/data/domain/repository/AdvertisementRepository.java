package penelope.corretagem.penelopeapirest.data.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import penelope.corretagem.penelopeapirest.data.domain.entity.AdvertisementEntity;
import java.util.Optional;

@Repository
public interface AdvertisementRepository extends JpaRepository<AdvertisementEntity, Long>, JpaSpecificationExecutor<AdvertisementEntity> {

    //Lista o ultimo anuncio cadastrado
    @Query("SELECT a FROM AdvertisementEntity a ORDER BY a.createdAt DESC Limit 1")
    Optional<AdvertisementEntity> findTopByOrderByCreatedAtDesc();

    // Lista um anuncio pelo ID
    @Query("""
    SELECT a
    FROM AdvertisementEntity a
    JOIN FETCH a.property e
    JOIN FETCH e.address address
    JOIN FETCH e.standAddress standAddress
    LEFT JOIN FETCH a.creator creator
    LEFT JOIN FETCH a.responsible responsible
    LEFT JOIN FETCH e.images images
    LEFT JOIN FETCH images.type tipo
    LEFT JOIN FETCH e.amenities amenities
    WHERE a.id = :id
    """)
    Optional<AdvertisementEntity> findByIdWithAllRelations(Long id);
}
