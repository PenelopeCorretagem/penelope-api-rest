package penelope.corretagem.penelopeapirest.data.domain.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import penelope.corretagem.penelopeapirest.data.domain.entity.ImageEstateEntity;

public interface ImageEstateRepository extends JpaRepository<ImageEstateEntity, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO imagem_empreendimento (fk_empreendimento, fk_tipo_imagem, url) " +
            "VALUES (:fkEstate, :fkImageType, :url)", nativeQuery = true)
    void insertImageNative(@Param("fkEstate") Long fkEstate,
                           @Param("fkImageType") int fkImageType,
                           @Param("url") String url);

    @Query(value = "SELECT LAST_INSERT_ID()", nativeQuery = true)
    Long getLastInsertId();
}
