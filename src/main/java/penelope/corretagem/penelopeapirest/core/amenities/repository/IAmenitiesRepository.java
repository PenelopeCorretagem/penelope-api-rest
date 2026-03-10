package penelope.corretagem.penelopeapirest.core.amenities.repository;

import penelope.corretagem.penelopeapirest.core.amenities.Amenities;
import java.util.List;

public interface IAmenitiesRepository {
    List<Amenities> findAll();
}
