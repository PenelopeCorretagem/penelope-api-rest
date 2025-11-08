package penelope.corretagem.penelopeapirest.data.domain.dto;

import java.time.LocalDate;

public record AdvertisementRequest(
        String id,
        UserRequest creator,
        UserRequest responsible,
        boolean active,
        boolean emphasis,
        LocalDate startDate,
        LocalDate endDate
) {
}
