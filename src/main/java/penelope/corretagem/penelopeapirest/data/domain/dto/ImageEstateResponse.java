package penelope.corretagem.penelopeapirest.data.domain.dto;

import penelope.corretagem.penelopeapirest.data.domain.entity.ImageEstateEntity;

public record ImageEstateResponse(
        Long id,
        EstateRequest estate,
        ImageEstateEntity type,
        String url
) {
}