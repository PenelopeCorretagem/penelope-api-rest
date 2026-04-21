package penelope.corretagem.penelopeapirest.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import penelope.corretagem.penelopeapirest.infrastructure.entity.AmenitiesJpaEntity;

import java.util.Optional;

public interface IAmenitiesJpaRepository extends JpaRepository<AmenitiesJpaEntity, Long> {
    Optional<AmenitiesJpaEntity> findByDescription(String description);
}
