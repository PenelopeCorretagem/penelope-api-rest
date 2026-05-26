package penelope.corretagem.penelopeapirest.infrastructure.adapter;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import penelope.corretagem.penelopeapirest.core.advertisement.Advertisement;
import penelope.corretagem.penelopeapirest.core.advertisement.AdvertisementFilter;
import penelope.corretagem.penelopeapirest.core.advertisement.repository.IAdvertisementRepository;
import penelope.corretagem.penelopeapirest.core.exception.DomainValidationException;
import penelope.corretagem.penelopeapirest.core.exception.ResourceNotFoundException;
import penelope.corretagem.penelopeapirest.core.estate.Estate;
import penelope.corretagem.penelopeapirest.infrastructure.entity.*;
import penelope.corretagem.penelopeapirest.infrastructure.entity.AmenitiesEstateJpaId;
import penelope.corretagem.penelopeapirest.infrastructure.mapper.AdvertisementInfrastructureMapper;
import penelope.corretagem.penelopeapirest.infrastructure.repository.IAddressJpaRepository;
import penelope.corretagem.penelopeapirest.infrastructure.repository.IAdvertisementJpaRepository;
import penelope.corretagem.penelopeapirest.infrastructure.repository.IAmenitiesJpaRepository;
import penelope.corretagem.penelopeapirest.infrastructure.repository.IEstateJpaRepository;
import penelope.corretagem.penelopeapirest.infrastructure.specification.AdvertisementSpecifications;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class AdvertisementRepositoryAdapter implements IAdvertisementRepository {

    private final IAdvertisementJpaRepository jpaRepository;
    private final AdvertisementInfrastructureMapper mapper;
    private final IEstateJpaRepository estateRepository;
    private final IAddressJpaRepository addressJpaRepository;
    private final IAmenitiesJpaRepository amenitiesJpaRepository;

    public AdvertisementRepositoryAdapter(
            IAdvertisementJpaRepository jpaRepository,
            AdvertisementInfrastructureMapper mapper,
            IEstateJpaRepository estateRepository,
            IAddressJpaRepository addressJpaRepository,
            IAmenitiesJpaRepository amenitiesJpaRepository
    ) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
        this.estateRepository = estateRepository;
        this.addressJpaRepository = addressJpaRepository;
        this.amenitiesJpaRepository = amenitiesJpaRepository;
    }

    @Override
    public Optional<Advertisement> findById(Long id) {
        return jpaRepository.findByIdWithAllRelations(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Advertisement> findAll(AdvertisementFilter filter) {

        EstateJpaEntity.Type type = null;
        if (filter.type() != null) {
            try {
                Estate.Type domainType = Estate.Type.fromExternalValue(filter.type());
                type = EstateJpaEntity.Type.fromCode(domainType.getCode());
            } catch (IllegalArgumentException ex) {
                throw new DomainValidationException("Tipo do imovel invalido: " + filter.type());
            }
        }

        Specification<AdvertisementJpaEntity> spec =
                AdvertisementSpecifications.hasCidade(filter.city())
                        .and(AdvertisementSpecifications.hasRegiao(filter.region()))
                        .and(AdvertisementSpecifications.hasTipo(type))
                        .and(AdvertisementSpecifications.hasQuartos(filter.numberOfRooms()))
                        .and(AdvertisementSpecifications.hasArea(filter.area()))
                        .and(AdvertisementSpecifications.hasTitulo(filter.title()))
                        .and(AdvertisementSpecifications.hasDescricao(filter.description()))
                        .and(AdvertisementSpecifications.isActive(filter.active()))
                        .and(AdvertisementSpecifications.createdAtEquals(filter.createdAt()))
                        .and(AdvertisementSpecifications.createdAtGreaterThan(filter.createdAtMin()))
                        .and(AdvertisementSpecifications.createdAtLessThan(filter.createdAtMax()));

        var entidades = jpaRepository.findAll(spec);

        return entidades.stream()
                .map(mapper::toDomain)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public Optional<Advertisement> findLatest(boolean includeInactive) {
        Optional<AdvertisementJpaEntity> entity = includeInactive
                ? jpaRepository.findTopByOrderByCreatedAtDesc()
                : jpaRepository.findTopByActiveTrueOrderByCreatedAtDesc();

        return entity.map(mapper::toDomain);
    }

    @Override
    public Advertisement findByEstateId(Long estateId, boolean includeInactive) {
        var entity = includeInactive
                ? jpaRepository.findByEstateId(estateId)
                : jpaRepository.findByEstateIdAndActiveTrue(estateId);
        return mapper.toDomain(entity);
    }

    @Override
    @Transactional
    public Advertisement save(Advertisement advertisement) {
        var jpaEntity = mapper.toEntity(advertisement);
        var estateEntity = jpaEntity.getEstate();

        attachManagedAmenities(estateEntity);

        var savedAddress = addressJpaRepository.saveAndFlush(estateEntity.getAddress());
        estateEntity.setAddress(savedAddress);

        var savedEstate = estateRepository.saveAndFlush(estateEntity);
        jpaEntity.setEstate(savedEstate);

        var savedAdvertisement = jpaRepository.save(jpaEntity);

        return mapper.toDomain(savedAdvertisement);
    }

    @Override
    public boolean existsByEstateTitle(String title) {
        return jpaRepository.existsByEstateTitleIgnoreCase(title);
    }

    @Override
    public boolean existsByEstateTitleAndIdNot(String title, Long advertisementId) {
        return jpaRepository.existsByEstateTitleIgnoreCaseAndIdNot(title, advertisementId);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        AdvertisementJpaEntity existingEntity = jpaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Anúncio não encontrado"));

        jpaRepository.delete(existingEntity);
    }

    @Override
    @Transactional
    public Advertisement update(Advertisement advertisement) {
        AdvertisementJpaEntity existingEntity = jpaRepository.findById(advertisement.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Anúncio não encontrado"));

        existingEntity.setActive(advertisement.getActive());
        existingEntity.setEmphasis(advertisement.getEmphasis());

        Estate estateDomain = advertisement.getEstate();
        EstateJpaEntity existingEstate = existingEntity.getEstate();

        existingEstate.setTitle(estateDomain.getTitle());
        existingEstate.setDescription(estateDomain.getDescription());
        existingEstate.setArea(estateDomain.getArea());
        existingEstate.setNumberOfRooms(estateDomain.getNumberOfRooms());
        existingEstate.setType(EstateJpaEntity.Type.fromCode(estateDomain.getType().getCode()));

        existingEstate.getAddress().setStreet(estateDomain.getAddress().getStreet());
        existingEstate.getAddress().setNumber(estateDomain.getAddress().getNumber());
        existingEstate.getAddress().setNeighborhood(estateDomain.getAddress().getNeighborhood());
        existingEstate.getAddress().setCity(estateDomain.getAddress().getCity());
        existingEstate.getAddress().setUf(estateDomain.getAddress().getUf());
        existingEstate.getAddress().setZipCode(estateDomain.getAddress().getZipCode());
        existingEstate.getAddress().setComplement(estateDomain.getAddress().getComplement());
        existingEstate.getAddress().setRegion(estateDomain.getAddress().getRegion());

        Set<Long> incomingAmenityIds = estateDomain.getAmenities().stream()
                .map(am -> am.getAmenity().getId())
                .collect(java.util.stream.Collectors.toSet());

        existingEstate.getAmenities().removeIf(rel -> !incomingAmenityIds.contains(rel.getAmenity().getId()));

        Set<Long> currentAmenityIds = existingEstate.getAmenities().stream()
                .map(rel -> rel.getAmenity().getId())
                .collect(java.util.stream.Collectors.toSet());

        for (Long incomingId : incomingAmenityIds) {
            if (!currentAmenityIds.contains(incomingId)) {
                if (!amenitiesJpaRepository.existsById(incomingId)) {
                    throw new ResourceNotFoundException("Amenidade não encontrada: " + incomingId);
                }

                var newRel = new AmenitiesEstateJpaEntity();
                newRel.setId(new AmenitiesEstateJpaId());
                newRel.setEstate(existingEstate);

                var amenityRef = amenitiesJpaRepository.getReferenceById(incomingId);
                newRel.setAmenity(amenityRef);

                existingEstate.getAmenities().add(newRel);
            }
        }

        existingEstate.getImages().clear();
        if (estateDomain.getImages() != null) {
            for (var imgDomain : estateDomain.getImages()) {
                var imgEntity = new ImageEstateJpaEntity();
                imgEntity.setUrl(imgDomain.getUrl());
                imgEntity.setEstate(existingEstate);

                var typeEntity = new ImageEstateTypeJpaEntity();
                typeEntity.setId(imgDomain.getType().getId());
                imgEntity.setType(typeEntity);

                existingEstate.getImages().add(imgEntity);
            }
        }

        var savedAdvertisement = jpaRepository.save(existingEntity);
        return mapper.toDomain(savedAdvertisement);
    }

    @Override
    @Transactional
    public void updateStatus(Long id, Boolean active) {
        AdvertisementJpaEntity existingEntity = jpaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Anúncio não encontrado"));

        existingEntity.setActive(active);
        jpaRepository.save(existingEntity);
    }

    private void attachManagedAmenities(EstateJpaEntity estateEntity) {
        if (estateEntity == null || estateEntity.getAmenities() == null) {
            return;
        }

        estateEntity.getAmenities().forEach(relation -> {
            if (relation.getAmenity() == null || relation.getAmenity().getId() == null) {
                throw new ResourceNotFoundException("Amenidade inválida para associação com o empreendimento");
            }

            if (!amenitiesJpaRepository.existsById(relation.getAmenity().getId())) {
                throw new ResourceNotFoundException("Amenidade não encontrada: " + relation.getAmenity().getId());
            }

            relation.setAmenity(amenitiesJpaRepository.getReferenceById(relation.getAmenity().getId()));
            relation.setEstate(estateEntity);
        });
    }
}
