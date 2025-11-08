package penelope.corretagem.penelopeapirest.data.domain.dto;

import penelope.corretagem.penelopeapirest.data.domain.entity.ImageEstateEntity;

public record ImageEstateResponse(
        String id,
        EstateRequest estate,
        ImageEstateEntity type,
        String url
) {
}