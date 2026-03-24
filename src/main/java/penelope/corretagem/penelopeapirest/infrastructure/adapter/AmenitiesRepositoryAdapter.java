package penelope.corretagem.penelopeapirest.infrastructure.adapter;

import org.springframework.stereotype.Component;
import penelope.corretagem.penelopeapirest.core.amenities.Amenities;
import penelope.corretagem.penelopeapirest.core.amenities.repository.IAmenitiesRepository;
import penelope.corretagem.penelopeapirest.infrastructure.mapper.AdvertisementInfrastructureMapper;
import penelope.corretagem.penelopeapirest.infrastructure.repository.IAmenitiesJpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AmenitiesRepositoryAdapter implements IAmenitiesRepository {

    private final IAmenitiesJpaRepository jpaRepository;
    private final AdvertisementInfrastructureMapper mapper;

    public AmenitiesRepositoryAdapter(IAmenitiesJpaRepository jpaRepository, AdvertisementInfrastructureMapper mapper) {

        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
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

    @Override
    public Amenities findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toAmenitiesDomain)
                .orElseThrow(() -> new RuntimeException("Comodidade não encontrada"));
    }
}
