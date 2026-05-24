package penelope.corretagem.penelopeapirest.application.dto;

public record AdvertisementCreateRequest(
        Boolean active,
        Boolean featured,
        Long creatorId,
        Long responsibleId,
        EstateCreateRequest estate) {
}
