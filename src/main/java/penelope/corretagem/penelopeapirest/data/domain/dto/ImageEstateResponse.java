package penelope.corretagem.penelopeapirest.data.domain.dto;

import penelope.corretagem.penelopeapirest.core.estate.ImageEstate;

public record ImageEstateResponse(
        Long id,
        EstateRequest estate,
        ImageEstate type,
        String url
) {
}