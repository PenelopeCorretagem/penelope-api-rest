package penelope.corretagem.penelopeapirest.data.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import penelope.corretagem.penelopeapirest.data.domain.entity.CityEntity;

public interface CityRepository extends JpaRepository<CityEntity, Long> {}