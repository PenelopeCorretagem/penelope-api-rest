//package penelope.corretagem.penelopeapirest.data.domain.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import penelope.corretagem.penelopeapirest.core.estate.Estate;
//import penelope.corretagem.penelopeapirest.data.domain.entity.EstateEntity;
//
//public interface EstateRepository extends JpaRepository<EstateEntity, Long> {
//
//    @Modifying
//    @Query(value = "INSERT INTO empreendimento (titulo, descricao, area, quartos, tipo, fk_endereco, fk_endereco_stand) " +
//            "VALUES (:title, :description, :area, :numberOfRooms, :type, :fkEndereco, :fkEnderecoStand)", nativeQuery = true)
//    void createEstateNative(@Param("title") String title,
//                            @Param("description") String description,
//                            @Param("area") Double area,
//                            @Param("numberOfRooms") Integer numberOfRooms,
//                            @Param("type") String type,
//                            @Param("fkEndereco") Long fkEndereco,
//                            @Param("fkEnderecoStand") Long fkEnderecoStand);
//
//    @Query(value = "SELECT LAST_INSERT_ID()", nativeQuery = true)
//    Long getLastInsertId();
//
//    @Modifying(clearAutomatically = true)
//    @Query(value = """
//        UPDATE empreendimento
//        SET titulo = :title,
//            descricao = :description,
//            area = :area,
//            quartos = :rooms,
//            tipo = :type
//        WHERE id = :id
//        """, nativeQuery = true)
//    void updateEstate(
//            Long id,
//            String title,
//            String description,
//            Double area,
//            Integer rooms,
//            String type);
//
//    @Modifying
//    @Query("UPDATE EstateEntity e SET e.standAddress.id = :standAddressId WHERE e.id = :estateId")
//    void updateEstateStandAddressId(@Param("estateId") Long estateId,
//                                    @Param("standAddressId") Long standAddressId);
//
//}