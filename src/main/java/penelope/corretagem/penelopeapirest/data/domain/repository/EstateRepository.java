package penelope.corretagem.penelopeapirest.data.domain.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import penelope.corretagem.penelopeapirest.data.domain.entity.EstateEntity;

import java.util.Optional;

public interface EstateRepository extends JpaRepository<EstateEntity, Long> {

    @Modifying
    @Query(value = "INSERT INTO empreendimento (titulo, descricao, area, quartos, tipo, fk_endereco, fk_endereco_stand) " +
            "VALUES (:title, :description, :area, :numberOfRooms, :type, :fkEndereco, :fkEnderecoStand)", nativeQuery = true)
    void createEstateNative(@Param("title") String title,
                            @Param("description") String description,
                            @Param("area") Double area,
                            @Param("numberOfRooms") Integer numberOfRooms,
                            @Param("type") String type,
                            @Param("fkEndereco") Long fkEndereco,
                            @Param("fkEnderecoStand") Long fkEnderecoStand);

    @Query(value = "SELECT LAST_INSERT_ID()", nativeQuery = true)
    Long getLastInsertId();
}