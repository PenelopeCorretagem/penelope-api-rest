package penelope.corretagem.penelopeapirest.data.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import penelope.corretagem.penelopeapirest.data.domain.entity.EstateAgentEntity;

public interface EstateAgentRepository extends JpaRepository<EstateAgentEntity, Long> {}