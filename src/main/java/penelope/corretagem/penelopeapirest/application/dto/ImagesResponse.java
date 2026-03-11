package penelope.corretagem.penelopeapirest.application.dto;

import penelope.corretagem.penelopeapirest.core.estate.ImageEstate;

public record ImagesResponse(
        Long id,
        String url,
        String type
) {
    public static ImagesResponse fromDomain(ImageEstate imageEstate) {
        return new ImagesResponse(
                imageEstate.getId(),
                imageEstate.getUrl(),
                imageEstate.getType().getDescription()
        );
    }
}

