package penelope.corretagem.penelopeapirest.infrastructure.adapter;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import penelope.corretagem.penelopeapirest.core.advertisement.Advertisement;
import penelope.corretagem.penelopeapirest.core.advertisement.repository.IAdvertisementRepository;
import penelope.corretagem.penelopeapirest.application.dto.AdvertisementFilterRequest;
import penelope.corretagem.penelopeapirest.core.amenities.AmenitiesEstateId;
import penelope.corretagem.penelopeapirest.core.estate.Estate;
import penelope.corretagem.penelopeapirest.infrastructure.entity.*;
import penelope.corretagem.penelopeapirest.infrastructure.mapper.AdvertisementInfrastructureMapper;
import penelope.corretagem.penelopeapirest.infrastructure.repository.IAddressJpaRepository;
import penelope.corretagem.penelopeapirest.infrastructure.repository.IAdvertisementJpaRepository;
import penelope.corretagem.penelopeapirest.infrastructure.repository.IEstateJpaRepository;
import penelope.corretagem.penelopeapirest.infrastructure.repository.IEventTypeJpaRepository;
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
    private final IEventTypeJpaRepository eventTypeRepository;

    public AdvertisementRepositoryAdapter(
            IAdvertisementJpaRepository jpaRepository,
            AdvertisementInfrastructureMapper mapper,
            IEstateJpaRepository estateRepository,
            IAddressJpaRepository addressJpaRepository,
            IEventTypeJpaRepository eventTypeRepository
    ) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
        this.estateRepository = estateRepository;
        this.addressJpaRepository = addressJpaRepository;
        this.eventTypeRepository = eventTypeRepository;
    }

    @Override
    public Optional<Advertisement> findById(Long id) {
        return jpaRepository.findByIdWithAllRelations(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Advertisement> findAll(AdvertisementFilterRequest filter) {

        Estate.Type type = null;
        if (filter.type() != null && !filter.type().isBlank()) type = Estate.Type.valueOf(filter.type().toUpperCase());

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
                        .and(AdvertisementSpecifications.createdAtLessThan(filter.createdAtMax()))
                        .and(AdvertisementSpecifications.endDateEquals(filter.endDate()))
                        .and(AdvertisementSpecifications.endDateGreaterThan(filter.endDateMin()))
                        .and(AdvertisementSpecifications.endDateLessThan(filter.endDateMax()));

        var entidades = jpaRepository.findAll(spec);

        return entidades.stream()
                .map(mapper::toDomain)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public Optional<Advertisement> findTopByOrderByCreatedAtDesc() {
        return jpaRepository.findTopByOrderByCreatedAtDesc()
                .map(mapper::toDomain);
    }

    @Override
    public Advertisement findByEstateId(Long estateId) {
        var entity = jpaRepository.findByEstateId(estateId);
        return mapper.toDomain(entity);
    }

    @Override
    public List<Advertisement> findExpiredActiveAdvertisements() {
        return jpaRepository.findExpiredActiveAdvertisements().stream()
                .map(mapper::toDomain)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    @Transactional
    public Advertisement save(Advertisement advertisement) {
        var jpaEntity = mapper.toEntity(advertisement);
        var estateEntity = jpaEntity.getEstate();

        var savedAddress = addressJpaRepository.saveAndFlush(estateEntity.getAddress());
        estateEntity.setAddress(savedAddress);

        if (estateEntity.getStandAddress() != null) {
            var savedStandAddress = addressJpaRepository.saveAndFlush(estateEntity.getStandAddress());
            estateEntity.setStandAddress(savedStandAddress);
        }

        var savedEstate = estateRepository.saveAndFlush(estateEntity);
        jpaEntity.setEstate(savedEstate);

        if (jpaEntity.getEventType() != null) {
            var savedEventType = eventTypeRepository.saveAndFlush(jpaEntity.getEventType());
            jpaEntity.setEventType(savedEventType);
        }

        var savedAdvertisement = jpaRepository.save(jpaEntity);

        return mapper.toDomain(savedAdvertisement);
    }

    @Override
    @Transactional
    public Advertisement update(Advertisement advertisement) {
        AdvertisementJpaEntity existingEntity = jpaRepository.findById(advertisement.getId())
                .orElseThrow(() -> new RuntimeException("Anúncio não encontrado"));

        existingEntity.setActive(advertisement.getActive());
        existingEntity.setEmphasis(advertisement.getEmphasis());
        existingEntity.setEndDate(advertisement.getEndDate());

        if (advertisement.getEventType() != null) {
            var evEntity = new EventTypeJpaEntity();
            evEntity.setId(advertisement.getEventType().getId());
            evEntity.setTitle(advertisement.getEventType().getTitle());
            evEntity.setSlug(advertisement.getEventType().getSlug());

            eventTypeRepository.save(evEntity);
            existingEntity.setEventType(evEntity);
        }

        Estate estateDomain = advertisement.getEstate();
        EstateJpaEntity existingEstate = existingEntity.getEstate();

        existingEstate.setTitle(estateDomain.getTitle());
        existingEstate.setDescription(estateDomain.getDescription());
        existingEstate.setArea(estateDomain.getArea());
        existingEstate.setNumberOfRooms(estateDomain.getNumberOfRooms());
        existingEstate.setType(EstateJpaEntity.Type.valueOf(estateDomain.getType().name()));

        existingEstate.getAddress().setStreet(estateDomain.getAddress().getStreet());
        existingEstate.getAddress().setNumber(estateDomain.getAddress().getNumber());
        existingEstate.getAddress().setNeighborhood(estateDomain.getAddress().getNeighborhood());
        existingEstate.getAddress().setCity(estateDomain.getAddress().getCity());
        existingEstate.getAddress().setUf(estateDomain.getAddress().getUf());
        existingEstate.getAddress().setZipCode(estateDomain.getAddress().getZipCode());
        existingEstate.getAddress().setComplement(estateDomain.getAddress().getComplement());
        existingEstate.getAddress().setRegion(estateDomain.getAddress().getRegion());

        var mappedNewTree = mapper.toEntity(advertisement);

        Set<Long> incomingAmenityIds = estateDomain.getAmenities().stream()
                .map(am -> am.getAmenity().getId())
                .collect(java.util.stream.Collectors.toSet());

        existingEstate.getAmenities().removeIf(rel -> !incomingAmenityIds.contains(rel.getAmenity().getId()));

        Set<Long> currentAmenityIds = existingEstate.getAmenities().stream()
                .map(rel -> rel.getAmenity().getId())
                .collect(java.util.stream.Collectors.toSet());

        for (Long incomingId : incomingAmenityIds) {
            if (!currentAmenityIds.contains(incomingId)) {
                var newRel = new AmenitiesEstateJpaEntity();
                newRel.setId(new AmenitiesEstateId());
                newRel.setEstate(existingEstate);

                var amenityRef = new AmenitiesJpaEntity();
                amenityRef.setId(incomingId);
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
                .orElseThrow(() -> new RuntimeException("Anúncio não encontrado"));

        existingEntity.setActive(active);
        jpaRepository.save(existingEntity);
    }
}