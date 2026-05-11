package penelope.corretagem.penelopeapirest.infrastructure.mapper;

import org.springframework.stereotype.Component;
import penelope.corretagem.penelopeapirest.core.amenities.Amenities;
import penelope.corretagem.penelopeapirest.core.amenities.AmenitiesEstate;
import penelope.corretagem.penelopeapirest.core.amenities.AmenitiesEstateId;
import penelope.corretagem.penelopeapirest.infrastructure.entity.AmenitiesEstateJpaEntity;
import penelope.corretagem.penelopeapirest.infrastructure.entity.AmenitiesEstateJpaId;
import penelope.corretagem.penelopeapirest.infrastructure.entity.AmenitiesJpaEntity;

@Component
public class AmenitiesInfrastructureMapper {

    public Amenities toDomain(AmenitiesJpaEntity jpa) {
        if (jpa == null) return null;

        return Amenities.restore(
                jpa.getId(),
                jpa.getDescription(),
                jpa.getIcon(),
                null
        );
    }

    public AmenitiesJpaEntity toEntity(Amenities domain) {
        if (domain == null) return null;

        var entity = new AmenitiesJpaEntity();
        entity.setId(domain.getId());
        entity.setDescription(domain.getDescription());
        entity.setIcon(domain.getIcon());

        return entity;
    }

    public void updateEntity(Amenities domain, AmenitiesJpaEntity entity) {
        if (domain == null || entity == null) return;
        entity.setDescription(domain.getDescription());
        entity.setIcon(domain.getIcon());
    }

    public AmenitiesEstate toRelationDomain(AmenitiesEstateJpaEntity jpaRelation) {
        if (jpaRelation == null) return null;

        AmenitiesEstateId id = null;
        if (jpaRelation.getId() != null) {
            id = new AmenitiesEstateId(
                    jpaRelation.getId().getEstate(),
                    jpaRelation.getId().getAmenity()
            );
        }

        return AmenitiesEstate.restore(
                id,
                null,
                toDomain(jpaRelation.getAmenity())
        );
    }

    public AmenitiesEstateJpaEntity toRelationEntity(AmenitiesEstate domainRelation) {
        if (domainRelation == null || domainRelation.getAmenity() == null) return null;

        var relation = new AmenitiesEstateJpaEntity();
        relation.setId(new AmenitiesEstateJpaId());

        var amenityEntity = new AmenitiesJpaEntity();
        amenityEntity.setId(domainRelation.getAmenity().getId());
        relation.setAmenity(amenityEntity);

        return relation;
    }
}
