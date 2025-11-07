package penelope.corretagem.penelopeapirest.data.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import penelope.corretagem.penelopeapirest.data.domain.entity.AddressEntity;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {}