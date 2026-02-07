package penelope.corretagem.penelopeapirest.data.domain.dto;

import penelope.corretagem.penelopeapirest.data.domain.entity.ImageEstateEntity;

public record ImageEstateRequest(
        Long id,
        EstateRequest estate,
        ImageEstateEntity type,
        String url
) {
}