package penelope.corretagem.penelopeapirest.application.dto;

import java.time.LocalDate;

public record AdvertisementCreateRequest(
        Long creator,
        Long responsible,
        LocalDate dataFim,
        Boolean active
) {
}
