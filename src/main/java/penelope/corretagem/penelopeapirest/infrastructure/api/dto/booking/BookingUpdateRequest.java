package penelope.corretagem.penelopeapirest.infrastructure.api.dto.booking;

import java.time.OffsetDateTime;

public record BookingUpdateRequest(
        OffsetDateTime startTime,
        OffsetDateTime endTime,
        String reason
) {
}
