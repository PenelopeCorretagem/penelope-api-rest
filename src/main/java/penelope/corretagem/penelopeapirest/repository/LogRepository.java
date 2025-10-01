package penelope.corretagem.penelopeapirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import penelope.corretagem.penelopeapirest.entity.LogEntity;

public interface LogRepository extends JpaRepository<LogEntity,Long> { }
