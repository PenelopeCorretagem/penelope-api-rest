package penelope.corretagem.penelopeapirest.data.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record AppointmentUpdateRequest(
        @JsonProperty("startDateTime") LocalDateTime startDateTime,
        @JsonProperty("endDateTime") LocalDateTime endDateTime,
        @JsonProperty("durationMinutes") Integer durationMinutes
) {
}