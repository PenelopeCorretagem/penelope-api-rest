package penelope.corretagem.penelopeapirest.infrastructure.adapter;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import penelope.corretagem.penelopeapirest.core.amenities.Amenities;
import penelope.corretagem.penelopeapirest.core.amenities.repository.IAmenitiesRepository;
import penelope.corretagem.penelopeapirest.infrastructure.entity.AmenitiesJpaEntity;
import penelope.corretagem.penelopeapirest.infrastructure.mapper.AmenitiesInfrastructureMapper;
import penelope.corretagem.penelopeapirest.infrastructure.repository.IAmenitiesJpaRepository;
import penelope.corretagem.penelopeapirest.infrastructure.specifications.AmenitySpecification;

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
    public List<Amenities> findAll(int offset, int limit, String name, String initial, String sort) {
        int page = offset / limit;
        Sort.Direction direction = "DESC".equalsIgnoreCase(sort) ? Sort.Direction.DESC : Sort.Direction.ASC;
        var sortOrder = Sort.by(direction, "description").and(Sort.by(Sort.Direction.ASC, "id"));
        var pageable = PageRequest.of(page, limit, sortOrder);
        Specification<AmenitiesJpaEntity> spec = AmenitySpecification.search(name, initial);


        return jpaRepository.findAll(spec, pageable).getContent().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Amenities> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Amenities save(Amenities domain) {
        AmenitiesJpaEntity entity;
        if (domain.getId() != null) {
            entity = jpaRepository.findById(domain.getId())
                    .orElseGet(() -> mapper.toEntity(domain));
            mapper.updateEntity(domain, entity);
        } else {
            entity = mapper.toEntity(domain);
        }

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

    @Override
    public Optional<Amenities> findByDescription(String description) {
        return jpaRepository.findByDescription(description).map(mapper::toDomain);
    }

    @Override
    public long count(String name, String initial) {
        Specification<AmenitiesJpaEntity> spec = AmenitySpecification.search(name, initial);
        return jpaRepository.count(spec);
    }
}