package penelope.corretagem.penelopeapirest.infrastructure.api.dto.eventtype;

import java.time.OffsetDateTime;

public record EventTypeCalResponse(
        Long id,
        String title,
        String slug,
        Integer lengthInMinutes,
        String description,
        Boolean hidden,
        Integer minimumBookingNotice,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
