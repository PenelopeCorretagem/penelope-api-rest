package penelope.corretagem.penelopeapirest.data.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import penelope.corretagem.penelopeapirest.core.amenities.Amenities;
import penelope.corretagem.penelopeapirest.data.domain.entity.AmenitiesEntity;

public interface AmenitiesRepository extends JpaRepository<AmenitiesEntity, Long> {

}

