package penelope.corretagem.penelopeapirest.infrastructure.entity.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import penelope.corretagem.penelopeapirest.infrastructure.entity.EstateJpaEntity;

@Converter(autoApply = false)
public class EstateTypeAttributeConverter implements AttributeConverter<EstateJpaEntity.Type, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EstateJpaEntity.Type attribute) {
        return attribute != null ? attribute.getCode() : null;
    }

    @Override
    public EstateJpaEntity.Type convertToEntityAttribute(Integer dbData) {
        return dbData != null ? EstateJpaEntity.Type.fromCode(dbData) : null;
    }
}
