package penelope.corretagem.penelopeapirest.data.domain.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import penelope.corretagem.penelopeapirest.data.domain.entity.AdvertisementEntity;

import java.util.Date;
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

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO anuncio (fk_empreendimento, fk_criador, fk_responsavel, ativo, data_fim, fk_tipo_evento_cal) " +
            "VALUES (:fkEstate, :fkCreator, :fkResponsible, :active, :endDate, :eventTypeId)", nativeQuery = true)
    void insertAdvertisementNative(@Param("fkEstate") Long fkEstate,
                                   @Param("fkCreator") Long fkCreator,
                                   @Param("fkResponsible") Long fkResponsible,
                                   @Param("active") Boolean active,
                                   @Param("endDate") Date endDate,
                                   @Param("eventTypeId") Long eventTypeId);

    @Query(value = "SELECT LAST_INSERT_ID()", nativeQuery = true)
    Long getLastInsertId();

    @Modifying
    @Query(value = """
        UPDATE anuncio
        SET fk_criador = :creator,
            fk_responsavel = :responsible,
            ativo = :active,
            data_fim = :dataFim,
            fk_tipo_evento_cal = :eventTypeId
        WHERE fk_empreendimento = :estateId
        """, nativeQuery = true)
    void updateAdvertisement(
            Long estateId,
            Long creator,
            Long responsible,
            Boolean active,
            Date dataFim,
            Long eventTypeId);

    @Query(value = """
        SELECT * FROM anuncio 
        WHERE fk_empreendimento = :estateId
        """, nativeQuery = true)
    AdvertisementEntity findByEstateId(Long estateId);

    @Modifying
    @Transactional
    @Query(value = """
        UPDATE anuncio
        SET ativo = :active
        WHERE id = :id
        """, nativeQuery = true)
    void updateActive(@Param("id") Long id, @Param("active") Boolean active);

    AdvertisementEntity findByEventTypeId(long eventTypeId);
}
