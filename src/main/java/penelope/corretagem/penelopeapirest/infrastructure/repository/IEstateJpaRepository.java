package penelope.corretagem.penelopeapirest.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import penelope.corretagem.penelopeapirest.infrastructure.entity.EstateJpaEntity;

public interface IEstateJpaRepository extends JpaRepository<EstateJpaEntity, Long> { }
