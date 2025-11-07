package penelope.corretagem.penelopeapirest.data.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import penelope.corretagem.penelopeapirest.data.domain.entity.EstateEntity;

public interface EstateRepository extends JpaRepository<EstateEntity, Long> {
}