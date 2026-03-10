package penelope.corretagem.penelopeapirest.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import penelope.corretagem.penelopeapirest.infrastructure.entity.AmenitiesJpaEntity;

public interface IAmenitiesJpaRepository extends JpaRepository<AmenitiesJpaEntity, Long> { }
