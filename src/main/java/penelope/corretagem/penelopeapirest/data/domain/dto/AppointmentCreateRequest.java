package penelope.corretagem.penelopeapirest.data.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AppointmentCreateRequest(
        @NotNull @JsonProperty("clientId") Long clientId,
        @NotNull @JsonProperty("estateAgentId") Long estateAgentId,
        @NotNull @JsonProperty("estateId") Long estateId,
        @JsonProperty("durationMinutes") Integer durationMinutes,
        @NotNull @JsonProperty("startDateTime") LocalDateTime startDateTime,
        @JsonProperty("endDateTime") LocalDateTime endDateTime
) {
}