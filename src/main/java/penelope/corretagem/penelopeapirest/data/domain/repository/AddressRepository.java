package penelope.corretagem.penelopeapirest.data.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import penelope.corretagem.penelopeapirest.data.domain.entity.AddressEntity;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
    @Modifying
    @Query(value = """
        UPDATE address
        SET street = :street,
            number = :number,
            neighborhood = :neighborhood,
            city = :city,
            uf = :uf,
            zip_code = :zipCode,
            complement = :complement,
            region = :region
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