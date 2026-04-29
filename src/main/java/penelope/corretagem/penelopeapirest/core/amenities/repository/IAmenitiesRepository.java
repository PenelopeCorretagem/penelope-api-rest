package penelope.corretagem.penelopeapirest.core.amenities.repository;

import penelope.corretagem.penelopeapirest.core.amenities.Amenities;
import java.util.List;
import java.util.Optional;

public interface IAmenitiesRepository {
    List<Amenities> findAll();

    List<Amenities> findAll(int offset, int limit, String name, String initial, String sort);

    Optional<Amenities> findById(Long id);

    Amenities save(Amenities amenities);

    boolean existsById(Long id);

    Optional<Amenities> findByDescription(String description);

    void deleteById(Long id);

    long count(String name, String initial);
}
