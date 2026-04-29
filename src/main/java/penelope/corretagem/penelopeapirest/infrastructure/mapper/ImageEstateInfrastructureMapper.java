package penelope.corretagem.penelopeapirest.infrastructure.mapper;

import org.springframework.stereotype.Component;
import penelope.corretagem.penelopeapirest.core.estate.ImageEstate;
import penelope.corretagem.penelopeapirest.core.estate.ImageEstateType;
import penelope.corretagem.penelopeapirest.infrastructure.entity.ImageEstateJpaEntity;
import penelope.corretagem.penelopeapirest.infrastructure.entity.ImageEstateTypeJpaEntity;

@Component
public class ImageEstateInfrastructureMapper {

    public ImageEstate toDomain(ImageEstateJpaEntity jpaImage) {
        if (jpaImage == null) return null;

        ImageEstateType imageType = null;
        if (jpaImage.getType() != null) {
            imageType = ImageEstateType.restore(
                    jpaImage.getType().getId(),
                    jpaImage.getType().getDescription(),
                    null
            );
        }

        return ImageEstate.restore(
                jpaImage.getId(),
                null,
                imageType,
                jpaImage.getUrl()
        );
    }

    public ImageEstateJpaEntity toEntity(ImageEstate domain) {
        if (domain == null) return null;

        var imgEntity = new ImageEstateJpaEntity();
        imgEntity.setId(domain.getId());
        imgEntity.setUrl(domain.getUrl());

        if (domain.getType() != null) {
            var typeEntity = new ImageEstateTypeJpaEntity();
            typeEntity.setId(domain.getType().getId());
            imgEntity.setType(typeEntity);
        }

        return imgEntity;
    }
}
