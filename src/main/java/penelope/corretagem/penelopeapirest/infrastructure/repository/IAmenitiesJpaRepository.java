package penelope.corretagem.penelopeapirest.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import penelope.corretagem.penelopeapirest.infrastructure.entity.AmenitiesJpaEntity;

import java.util.Optional;

public interface IAmenitiesJpaRepository extends JpaRepository<AmenitiesJpaEntity, Long>, JpaSpecificationExecutor<AmenitiesJpaEntity> {
    Optional<AmenitiesJpaEntity> findByDescription(String description);
}
