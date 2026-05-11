package penelope.corretagem.penelopeapirest.infrastructure.mapper;

import org.springframework.stereotype.Component;
import penelope.corretagem.penelopeapirest.core.amenities.AmenitiesEstate;
import penelope.corretagem.penelopeapirest.core.estate.Estate;
import penelope.corretagem.penelopeapirest.core.estate.ImageEstate;
import penelope.corretagem.penelopeapirest.infrastructure.entity.AmenitiesEstateJpaEntity;
import penelope.corretagem.penelopeapirest.infrastructure.entity.EstateJpaEntity;
import penelope.corretagem.penelopeapirest.infrastructure.entity.ImageEstateJpaEntity;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EstateInfrastructureMapper {

    private final AddressInfrastructureMapper addressMapper;
    private final ImageEstateInfrastructureMapper imageMapper;
    private final AmenitiesInfrastructureMapper amenitiesMapper;

    public EstateInfrastructureMapper(
            AddressInfrastructureMapper addressMapper,
            ImageEstateInfrastructureMapper imageMapper,
            AmenitiesInfrastructureMapper amenitiesMapper
    ) {
        this.addressMapper = addressMapper;
        this.imageMapper = imageMapper;
        this.amenitiesMapper = amenitiesMapper;
    }

    public Estate toDomain(EstateJpaEntity jpa) {
        if (jpa == null) return null;

        Set<ImageEstate> imagesDomain = null;
        if (jpa.getImages() != null) {
            imagesDomain = jpa.getImages().stream()
                    .map(imageMapper::toDomain)
                    .collect(Collectors.toSet());
        }

        Set<AmenitiesEstate> amenitiesDomain = null;
        if (jpa.getAmenities() != null) {
            amenitiesDomain = jpa.getAmenities().stream()
                    .map(amenitiesMapper::toRelationDomain)
                    .collect(Collectors.toSet());
        }

        return Estate.restore(
                jpa.getId(),
                jpa.getTitle(),
                jpa.getDescription(),
                jpa.getArea(),
                jpa.getNumberOfRooms(),
            jpa.getType() != null ? Estate.Type.fromCode(jpa.getType().getCode()) : null,
                addressMapper.toDomain(jpa.getAddress()),
                imagesDomain,
                amenitiesDomain
        );
    }

    public EstateJpaEntity toEntity(Estate domain) {
        if (domain == null) return null;

        var entity = new EstateJpaEntity();

        entity.setId(domain.getId());
        entity.setTitle(domain.getTitle());
        entity.setDescription(domain.getDescription());
        entity.setArea(domain.getArea());
        entity.setNumberOfRooms(domain.getNumberOfRooms());

        if (domain.getType() != null) {
            entity.setType(EstateJpaEntity.Type.fromCode(domain.getType().getCode()));
        }

        entity.setAddress(addressMapper.toEntity(domain.getAddress()));

        if (domain.getAmenities() != null && !domain.getAmenities().isEmpty()) {
            Set<AmenitiesEstateJpaEntity> amenitiesSet = domain.getAmenities().stream()
                    .map(amenitiesMapper::toRelationEntity)
                    .collect(Collectors.toSet());

            amenitiesSet.forEach(relation -> relation.setEstate(entity));
            entity.setAmenities(amenitiesSet);
        }

        if (domain.getImages() != null && !domain.getImages().isEmpty()) {
            Set<ImageEstateJpaEntity> imagesSet = domain.getImages().stream()
                    .map(imageMapper::toEntity)
                    .collect(Collectors.toSet());

            imagesSet.forEach(image -> image.setEstate(entity));
            entity.setImages(imagesSet);
        }

        return entity;
    }
}
