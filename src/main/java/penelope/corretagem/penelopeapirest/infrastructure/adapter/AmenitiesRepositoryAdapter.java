package penelope.corretagem.penelopeapirest.infrastructure.adapter;

import org.springframework.stereotype.Component;
import penelope.corretagem.penelopeapirest.core.amenities.Amenities;
import penelope.corretagem.penelopeapirest.core.amenities.repository.IAmenitiesRepository;
import penelope.corretagem.penelopeapirest.infrastructure.repository.IAmenitiesJpaRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AmenitiesRepositoryAdapter implements IAmenitiesRepository {

    private final IAmenitiesJpaRepository jpaRepository;

    public AmenitiesRepositoryAdapter(IAmenitiesJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Amenities> findAll() {
        return jpaRepository.findAll().stream()
                .map(entity ->
                        new Amenities(
                                entity.getId(),
                                entity.getDescription()))
                .collect(Collectors.toList());
    }
}
