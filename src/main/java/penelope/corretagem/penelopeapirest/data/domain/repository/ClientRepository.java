package penelope.corretagem.penelopeapirest.data.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import penelope.corretagem.penelopeapirest.data.domain.entity.ClientEntity;

public interface ClientRepository  extends JpaRepository<ClientEntity, Long> {}
