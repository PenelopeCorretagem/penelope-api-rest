//package penelope.corretagem.penelopeapirest.data.domain.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import penelope.corretagem.penelopeapirest.core.address.Address;
//import penelope.corretagem.penelopeapirest.data.domain.entity.AddressEntity;
//import penelope.corretagem.penelopeapirest.infrastructure.entity.AddressJpaEntity;
//
//public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
//    @Modifying
//    @Query(value = """
//        UPDATE endereco
//        SET rua = :street,
//            numero = :number,
//            bairro = :neighborhood,
//            cidade = :city,
//            uf = :uf,
//            cep = :zipCode,
//            complemento = :complement,
//            regiao = :region
//        WHERE id = :id
//        """, nativeQuery = true)
//    void updateAddress(
//            Long id,
//            String street,
//            String number,
//            String neighborhood,
//            String city,
//            String uf,
//            String zipCode,
//            String complement,
//            String region);
//
//}