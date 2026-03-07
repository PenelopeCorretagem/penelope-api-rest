package penelope.corretagem.penelopeapirest.data.domain.dto;

import penelope.corretagem.penelopeapirest.core.estate.ImageEstate;

import java.util.Set;

public record ImageEstateTypeRequest(
        Long id,
        String description,
        Set<ImageEstate> images
) {
}
