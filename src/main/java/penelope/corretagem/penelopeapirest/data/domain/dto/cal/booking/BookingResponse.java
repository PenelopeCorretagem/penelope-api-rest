package penelope.corretagem.penelopeapirest.data.domain.dto.cal.booking;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

public record BookingResponse(
        Long id,
        String title,
        String status,
        @JsonProperty("start") OffsetDateTime startTime,
        @JsonProperty("end") OffsetDateTime endTime,
        Long eventTypeId,
        List<BookingAttendee> attendees,
        @JsonProperty("hosts") List<BookingOrganizer> organizer,
        Map<String, Object> metadata,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}