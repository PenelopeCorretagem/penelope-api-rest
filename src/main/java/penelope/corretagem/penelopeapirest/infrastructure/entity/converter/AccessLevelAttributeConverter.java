package penelope.corretagem.penelopeapirest.infrastructure.entity.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import penelope.corretagem.penelopeapirest.core.user.valueObject.AccessLevel;

@Converter(autoApply = false)
public class AccessLevelAttributeConverter implements AttributeConverter<AccessLevel, Integer> {

    @Override
    public Integer convertToDatabaseColumn(AccessLevel attribute) {
        return attribute != null ? attribute.getCode() : null;
    }

    @Override
    public AccessLevel convertToEntityAttribute(Integer dbData) {
        return dbData != null ? AccessLevel.fromCode(dbData) : null;
    }
}
