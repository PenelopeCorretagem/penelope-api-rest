package penelope.corretagem.penelopeapirest.data.domain.dto;

import penelope.corretagem.penelopeapirest.data.domain.entity.ImageEstateEntity;

import java.util.Set;

public record ImageEstateTypeRequest(
        Long id,
        String description,
        Set<ImageEstateEntity> images
) {
}
