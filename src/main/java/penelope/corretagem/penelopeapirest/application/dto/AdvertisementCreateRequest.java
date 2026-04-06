package penelope.corretagem.penelopeapirest.application.dto;

import java.time.LocalDate;

public record AdvertisementCreateRequest(
        Boolean active,
        Boolean featured,
        LocalDate endDate,
        Long creatorId,
        Long responsibleId,
        EstateCreateRequest estate) {
}
