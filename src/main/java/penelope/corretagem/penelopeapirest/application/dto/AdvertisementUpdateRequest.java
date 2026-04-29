package penelope.corretagem.penelopeapirest.application.dto;

import java.time.LocalDate;

public record AdvertisementUpdateRequest(
        Boolean active,
        Boolean featured,
        LocalDate endDate,
        Long responsibleId,
        EstateCreateRequest estate
) {
}