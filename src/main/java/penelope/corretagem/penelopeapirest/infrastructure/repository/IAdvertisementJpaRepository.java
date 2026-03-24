package penelope.corretagem.penelopeapirest.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import penelope.corretagem.penelopeapirest.infrastructure.entity.AdvertisementJpaEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAdvertisementJpaRepository extends JpaRepository<AdvertisementJpaEntity, Long>, JpaSpecificationExecutor<AdvertisementJpaEntity> {

    // Lista o ultimo anuncio cadastrado
    @Query("SELECT a FROM AdvertisementJpaEntity a ORDER BY a.createdAt DESC Limit 1")
    Optional<AdvertisementJpaEntity> findTopByOrderByCreatedAtDesc();

    // Lista um anuncio pelo ID trazendo todas as relações (Ajuste os nomes das propriedades conforme sua EstateEntity)
    @Query("""
            SELECT a
            FROM AdvertisementJpaEntity a
            JOIN FETCH a.estate e
            JOIN FETCH e.address address
            LEFT JOIN FETCH e.standAddress standAddress
            LEFT JOIN FETCH a.creator creator
            LEFT JOIN FETCH a.responsible responsible
            LEFT JOIN FETCH e.images images
            LEFT JOIN FETCH images.type tipo
            LEFT JOIN FETCH e.amenities amenities
            WHERE a.id = :id
            """)
    Optional<AdvertisementJpaEntity> findByIdWithAllRelations(@Param("id") Long id);

    @Query(value = """
            SELECT * FROM anuncio 
            WHERE fk_empreendimento = :estateId
            """, nativeQuery = true)
    AdvertisementJpaEntity findByEstateId(@Param("estateId") Long estateId);

    @Query("""
            SELECT a
            FROM AdvertisementJpaEntity a
            WHERE a.endDate < CURRENT_DATE
              AND a.active = true
            """)
    List<AdvertisementJpaEntity> findExpiredActiveAdvertisements();
}
