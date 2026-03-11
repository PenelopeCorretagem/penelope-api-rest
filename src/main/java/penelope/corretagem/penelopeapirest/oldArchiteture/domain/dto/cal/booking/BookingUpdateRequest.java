package penelope.corretagem.penelopeapirest.oldArchiteture.domain.dto.cal.booking;

import java.time.OffsetDateTime;

public record BookingUpdateRequest(
        OffsetDateTime startTime,
        OffsetDateTime endTime,
        String reason
) {
}