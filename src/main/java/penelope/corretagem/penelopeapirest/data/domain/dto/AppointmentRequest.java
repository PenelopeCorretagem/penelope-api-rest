package penelope.corretagem.penelopeapirest.data.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import penelope.corretagem.penelopeapirest.data.domain.enums.Status;

import java.time.LocalDateTime;

public record AppointmentRequest(
        Long id,
        @JsonProperty("clientId") Long clientId,
        @JsonProperty("estateAgentId") Long estateAgentId,
        @JsonProperty("estateId") Long estateId,
        @JsonProperty("durationMinutes") Integer durationMinutes,
        @JsonProperty("startDateTime") LocalDateTime startDateTime,
        @JsonProperty("endDateTime") LocalDateTime endDateTime,
        Status status,
        @JsonProperty("calBookingId") Long calBookingId
) {
}