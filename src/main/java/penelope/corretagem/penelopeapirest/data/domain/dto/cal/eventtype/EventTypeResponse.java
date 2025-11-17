package penelope.corretagem.penelopeapirest.data.domain.dto.cal.eventtype;

import java.time.OffsetDateTime;

public record EventTypeResponse(
        Long id,
        String title,
        String slug,
        Integer lengthInMinutes,
        String description,
        Boolean hidden,
        Integer minimumBookingNotice,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}