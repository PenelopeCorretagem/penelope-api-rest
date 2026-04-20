package penelope.corretagem.penelopeapirest.infrastructure.adapter;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import penelope.corretagem.penelopeapirest.core.amenities.Amenities;
import penelope.corretagem.penelopeapirest.core.amenities.repository.IAmenitiesRepository;
import penelope.corretagem.penelopeapirest.infrastructure.mapper.AmenitiesInfrastructureMapper;
import penelope.corretagem.penelopeapirest.infrastructure.repository.IAmenitiesJpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AmenitiesRepositoryAdapter implements IAmenitiesRepository {

    private final IAmenitiesJpaRepository jpaRepository;
    private final AmenitiesInfrastructureMapper mapper;

    public AmenitiesRepositoryAdapter(IAmenitiesJpaRepository jpaRepository, AmenitiesInfrastructureMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Amenities> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Amenities> findAll(int offset, int limit) {
        int page = offset / limit;
        var pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));

        return jpaRepository.findAll(pageable).getContent().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Amenities> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Amenities save(Amenities domain) {

        var entity = mapper.toEntity(domain);

        var savedEntity = jpaRepository.save(entity);

        return mapper.toDomain(savedEntity);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}