package penelope.corretagem.penelopeapirest.data.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import penelope.corretagem.penelopeapirest.core.amenities.Amenities;

public interface AmenitiesRepository extends JpaRepository<Amenities, Long> {

}

