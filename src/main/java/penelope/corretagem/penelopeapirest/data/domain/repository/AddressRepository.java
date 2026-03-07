package penelope.corretagem.penelopeapirest.data.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import penelope.corretagem.penelopeapirest.core.address.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
    @Modifying
    @Query(value = """
        UPDATE endereco
        SET rua = :street,
            numero = :number,
            bairro = :neighborhood,
            cidade = :city,
            uf = :uf,
            cep = :zipCode,
            complemento = :complement,
            regiao = :region
        WHERE id = :id
        """, nativeQuery = true)
    void updateAddress(
            Long id,
            String street,
            String number,
            String neighborhood,
            String city,
            String uf,
            String zipCode,
            String complement,
            String region);

}