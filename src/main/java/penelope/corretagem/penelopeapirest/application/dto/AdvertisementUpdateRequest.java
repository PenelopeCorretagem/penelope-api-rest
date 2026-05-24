package penelope.corretagem.penelopeapirest.application.dto;

public record AdvertisementUpdateRequest(
        Boolean active,
        Boolean featured,
        Long responsibleId,
        EstateCreateRequest estate
) {
}
