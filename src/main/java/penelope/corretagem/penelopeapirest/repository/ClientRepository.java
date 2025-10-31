package penelope.corretagem.penelopeapirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import penelope.corretagem.penelopeapirest.entity.ClientEntity;

public interface ClientRepository  extends JpaRepository<ClientEntity, Long> {}
