package penelope.corretagem.penelopeapirest.data.domain.dto;

import penelope.corretagem.penelopeapirest.data.domain.entity.ImageEstateEntity;

import java.util.Set;

public record ImageEstateTypeResponse(
        Long id,
        String description,
        Set<ImageEstateEntity> images
) {
}
