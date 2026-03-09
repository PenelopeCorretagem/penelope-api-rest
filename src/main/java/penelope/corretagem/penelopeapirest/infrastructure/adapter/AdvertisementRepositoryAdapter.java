package penelope.corretagem.penelopeapirest.infrastructure.adapter;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import penelope.corretagem.penelopeapirest.core.advertisement.Advertisement;
import penelope.corretagem.penelopeapirest.core.advertisement.repository.IAdvertisementRepository;
import penelope.corretagem.penelopeapirest.core.dto.AdvertisementFilterRequest;
import penelope.corretagem.penelopeapirest.core.estate.Estate;
import penelope.corretagem.penelopeapirest.infrastructure.entity.AdvertisementJpaEntity;
import penelope.corretagem.penelopeapirest.infrastructure.mapper.AdvertisementInfrastructureMapper;
import penelope.corretagem.penelopeapirest.infrastructure.repository.IAddressJpaRepository;
import penelope.corretagem.penelopeapirest.infrastructure.repository.IAdvertisementJpaRepository;
import penelope.corretagem.penelopeapirest.infrastructure.repository.IEstateJpaRepository;
import penelope.corretagem.penelopeapirest.infrastructure.repository.IEventTypeJpaRepository;
import penelope.corretagem.penelopeapirest.infrastructure.specification.AdvertisementSpecifications;

import java.util.List;
import java.util.Optional;

@Component
public class AdvertisementRepositoryAdapter implements IAdvertisementRepository {

    private final IAdvertisementJpaRepository jpaRepository;
    private final AdvertisementInfrastructureMapper mapper;
    private final IEstateJpaRepository estateRepository;
    private final IAddressJpaRepository addressJpaRepository;
    private final IEventTypeJpaRepository eventTypeRepository;

    public AdvertisementRepositoryAdapter(
            IAdvertisementJpaRepository jpaRepository,
            AdvertisementInfrastructureMapper mapper, IEstateJpaRepository estateRepository, IAddressJpaRepository addressJpaRepository, IEventTypeJpaRepository eventTypeRepository
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
            var eventTypeToSave = jpaEntity.getEventType();

            eventTypeRepository.insertNative(
                    eventTypeToSave.getId(),
                    eventTypeToSave.getTitle(),
                    eventTypeToSave.getSlug()
            );

            var eventTypeProxy = eventTypeRepository.getReferenceById(eventTypeToSave.getId());
            jpaEntity.setEventType(eventTypeProxy);
        }

        var savedAdvertisement = jpaRepository.save(jpaEntity);

        return mapper.toDomain(savedAdvertisement);
    }

    @Override
    public void deactivateById(Long id) {

    }
}