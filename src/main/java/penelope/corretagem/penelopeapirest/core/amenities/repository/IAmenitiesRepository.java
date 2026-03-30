package penelope.corretagem.penelopeapirest.core.amenities.repository;

import penelope.corretagem.penelopeapirest.application.dto.CreateAmenityRequest;
import penelope.corretagem.penelopeapirest.core.amenities.Amenities;
import java.util.List;
import java.util.Optional;

public interface IAmenitiesRepository {
    List<Amenities> findAll();
    Optional<Amenities> findById(Long id);
    Amenities save(Amenities amenities);
    boolean existsById(Long id);
    void deleteById(Long id);
}
