package penelope.corretagem.penelopeapirest.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import penelope.corretagem.penelopeapirest.infrastructure.entity.AddressJpaEntity;

public interface IAddressJpaRepository extends JpaRepository<AddressJpaEntity, Long> {
}