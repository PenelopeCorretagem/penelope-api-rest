package penelope.corretagem.penelopeapirest.infrastucture.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import penelope.corretagem.penelopeapirest.infrastucture.entity.AdvertisementJpaEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdvertisementJpaRepository extends JpaRepository<AdvertisementJpaEntity, Long> {

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

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO anuncio (fk_empreendimento, fk_criador, fk_responsavel, ativo, data_fim, fk_tipo_evento_cal) " +
            "VALUES (:fkEstate, :fkCreator, :fkResponsible, :active, :endDate, :eventTypeId)", nativeQuery = true)
    void insertAdvertisementNative(@Param("fkEstate") Long fkEstate,
                                   @Param("fkCreator") Long fkCreator,
                                   @Param("fkResponsible") Long fkResponsible,
                                   @Param("active") Boolean active,
                                   @Param("endDate") Date endDate, // Cuidado: No domínio usamos LocalDate, aqui Date. O Mapper cuidará disso ou você pode ajustar o banco para LocalDate.
                                   @Param("eventTypeId") Long eventTypeId);

    @Query(value = "SELECT LAST_INSERT_ID()", nativeQuery = true)
    Long getLastInsertId();

    @Modifying
    @Transactional
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
            @Param("estateId") Long estateId,
            @Param("creator") Long creator,
            @Param("responsible") Long responsible,
            @Param("active") Boolean active,
            @Param("dataFim") Date dataFim,
            @Param("eventTypeId") Long eventTypeId);

    @Query(value = """
        SELECT * FROM anuncio 
        WHERE fk_empreendimento = :estateId
        """, nativeQuery = true)
    AdvertisementJpaEntity findByEstateId(@Param("estateId") Long estateId);

    @Modifying
    @Transactional
    @Query(value = """
        UPDATE anuncio
        SET ativo = :active
        WHERE id = :id
        """, nativeQuery = true)
    void updateActive(@Param("id") Long id, @Param("active") Boolean active);

    @Query("""
    SELECT a
    FROM AdvertisementJpaEntity a
    WHERE a.endDate < CURRENT_DATE
      AND a.active = true
    """)
    List<AdvertisementJpaEntity> findExpiredActiveAdvertisements();

    @Modifying
    @Transactional
    @Query("""
    UPDATE AdvertisementJpaEntity a
    SET a.active = false
    WHERE a.id = :id
    """)
    void deactivateById(@Param("id") Long id);

    AdvertisementJpaEntity findByEventTypeId(long eventTypeId);
}
