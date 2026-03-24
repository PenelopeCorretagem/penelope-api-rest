package penelope.corretagem.penelopeapirest.infrastructure.mapper;

import org.springframework.stereotype.Component;
import penelope.corretagem.penelopeapirest.core.address.Address;
import penelope.corretagem.penelopeapirest.core.advertisement.Advertisement;
import penelope.corretagem.penelopeapirest.core.amenities.Amenities;
import penelope.corretagem.penelopeapirest.core.amenities.AmenitiesEstate;
import penelope.corretagem.penelopeapirest.core.amenities.AmenitiesEstateId;
import penelope.corretagem.penelopeapirest.core.estate.Estate;
import penelope.corretagem.penelopeapirest.core.estate.ImageEstate;
import penelope.corretagem.penelopeapirest.core.estate.ImageEstateType;
import penelope.corretagem.penelopeapirest.core.eventType.EventType;
import penelope.corretagem.penelopeapirest.infrastructure.entity.*;
import penelope.corretagem.penelopeapirest.infrastructure.repository.IAmenitiesJpaRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AdvertisementInfrastructureMapper {

    private final UserInfrastructureMapper userMapper;
    private final IAmenitiesJpaRepository amenitiesJpaRepository;

    public AdvertisementInfrastructureMapper(UserInfrastructureMapper userMapper, IAmenitiesJpaRepository amenitiesJpaRepository) {
        this.userMapper = userMapper;
        this.amenitiesJpaRepository = amenitiesJpaRepository;
    }

    public Advertisement toDomain(AdvertisementJpaEntity jpaEntity) {
        if (jpaEntity == null) return null;

        return Advertisement.restore(
                jpaEntity.getId(),
                toEstateDomain(jpaEntity.getEstate()),
                userMapper.toDomain(jpaEntity.getCreator()),
                userMapper.toDomain(jpaEntity.getResponsible()),
                jpaEntity.getActive(),
                jpaEntity.getEmphasis(),
                jpaEntity.getEndDate(),
                jpaEntity.getCreatedAt(),
                toEventTypeDomain(jpaEntity.getEventType())
        );
    }

    private Estate toEstateDomain(EstateJpaEntity jpa) {
        if (jpa == null) return null;

        Set<ImageEstate> imagesDomain = null;
        if (jpa.getImages() != null) {
            imagesDomain = jpa.getImages().stream()
                    .map(this::toImageEstateDomain)
                    .collect(Collectors.toSet());
        }

        Set<AmenitiesEstate> amenitiesDomain = null;
        if (jpa.getAmenities() != null) {
            amenitiesDomain = jpa.getAmenities().stream()
                    .map(this::toAmenitiesEstateDomain)
                    .collect(Collectors.toSet());
        }

        return Estate.restore(
                jpa.getId(),
                jpa.getTitle(),
                jpa.getDescription(),
                jpa.getArea(),
                jpa.getNumberOfRooms(),
                Estate.Type.valueOf(jpa.getType().name()),
                toAddressDomain(jpa.getAddress()),
                toAddressDomain(jpa.getStandAddress()),
                imagesDomain,
                amenitiesDomain
        );
    }

    private Address toAddressDomain(AddressJpaEntity jpaAddress) {
        if (jpaAddress == null) return null;

        return Address.restore(
                jpaAddress.getId(),
                jpaAddress.getStreet(),
                jpaAddress.getNumber(),
                jpaAddress.getNeighborhood(),
                jpaAddress.getCity(),
                jpaAddress.getUf(),
                jpaAddress.getZipCode(),
                jpaAddress.getComplement(),
                jpaAddress.getRegion()
        );
    }

    private ImageEstate toImageEstateDomain(ImageEstateJpaEntity jpaImage) {
        if (jpaImage == null) return null;

        ImageEstateType tipoImagem = null;
        if (jpaImage.getType() != null) {
            tipoImagem = ImageEstateType.restore(
                    jpaImage.getType().getId(),
                    jpaImage.getType().getDescription(),
                    null
            );
        }

        return ImageEstate.restore(
                jpaImage.getId(),
                null,
                tipoImagem,
                jpaImage.getUrl()
        );
    }

    private AmenitiesEstate toAmenitiesEstateDomain(AmenitiesEstateJpaEntity jpaAmenity) {
        if (jpaAmenity == null) return null;

        Amenities amenityDomain = toAmenitiesDomain(jpaAmenity.getAmenity());

        return AmenitiesEstate.restore(
                jpaAmenity.getId(),
                null,
                amenityDomain
        );
    }

    public Amenities toAmenitiesDomain(AmenitiesJpaEntity jpa) {
        if (jpa == null) return null;

        return new Amenities(
                jpa.getId(),
                jpa.getDescription()
        );
    }

    private EventType toEventTypeDomain(EventTypeJpaEntity jpa) {
        if (jpa == null) return null;

        return EventType.restore(
                jpa.getId(),
                jpa.getTitle(),
                jpa.getSlug()
        );
    }

    public AdvertisementJpaEntity toEntity(Advertisement domain) {
        if (domain == null) return null;

        var entity = new AdvertisementJpaEntity();
        entity.setId(domain.getId());

        EstateJpaEntity estateEntity = toEstateEntity(domain.getEstate());

        entity.setEstate(estateEntity);
        entity.setCreator(userMapper.toEntity(domain.getCreator()));
        entity.setResponsible(userMapper.toEntity(domain.getResponsible()));
        entity.setEventType(toEventTypeEntity(domain.getEventType()));

        entity.setActive(domain.getActive());
        entity.setEmphasis(domain.getEmphasis());
        entity.setEndDate(domain.getEndDate());
        entity.setCreatedAt(domain.getCreatedAt());

        return entity;
    }

    private EstateJpaEntity toEstateEntity(Estate domain) {
        if (domain == null) return null;

        var entity = new EstateJpaEntity();

        entity.setId(domain.getId());
        entity.setTitle(domain.getTitle());
        entity.setDescription(domain.getDescription());
        entity.setArea(domain.getArea());
        entity.setNumberOfRooms(domain.getNumberOfRooms());

        if (domain.getType() != null) {
            entity.setType(EstateJpaEntity.Type.valueOf(domain.getType().name()));
        }

        entity.setAddress(toAddressEntity(domain.getAddress()));
        entity.setStandAddress(toAddressEntity(domain.getStandAddress()));

        if (domain.getAmenities() != null && !domain.getAmenities().isEmpty()) {
            Set<AmenitiesEstateJpaEntity> amenitiesSet = domain.getAmenities().stream().map(amenitiesEstateDomain -> {
                var relacionamento = new AmenitiesEstateJpaEntity();

                relacionamento.setId(new AmenitiesEstateId());

                var amenityEntity = amenitiesJpaRepository.getReferenceById(amenitiesEstateDomain.getAmenity().getId());

                relacionamento.setAmenity(amenityEntity);
                relacionamento.setEstate(entity);

                return relacionamento;
            }).collect(Collectors.toSet());

            entity.setAmenities(amenitiesSet);
        }

        if (domain.getImages() != null && !domain.getImages().isEmpty()) {
            Set<ImageEstateJpaEntity> imagesSet = domain.getImages().stream().map(imgDomain -> {
                var imgEntity = new ImageEstateJpaEntity();

                imgEntity.setUrl(imgDomain.getUrl());

                var typeEntity = new ImageEstateTypeJpaEntity();
                typeEntity.setId(imgDomain.getType().getId());

                imgEntity.setType(typeEntity);
                imgEntity.setEstate(entity);

                return imgEntity;
            }).collect(Collectors.toSet());

            entity.setImages(imagesSet);
        }

        return entity;
    }

    private AddressJpaEntity toAddressEntity(Address domain) {
        if (domain == null) return null;

        var entity = new AddressJpaEntity();
        entity.setId(domain.getId());
        entity.setStreet(domain.getStreet());
        entity.setNumber(domain.getNumber());
        entity.setNeighborhood(domain.getNeighborhood());
        entity.setCity(domain.getCity());
        entity.setUf(domain.getUf());
        entity.setZipCode(domain.getZipCode());
        entity.setComplement(domain.getComplement());
        entity.setRegion(domain.getRegion());

        return entity;
    }

    private EventTypeJpaEntity toEventTypeEntity(EventType domain) {
        if (domain == null) return null;

        var entity = new EventTypeJpaEntity();
        entity.setId(domain.getId());
        entity.setTitle(domain.getTitle());
        entity.setSlug(domain.getSlug());

        return entity;
    }
}