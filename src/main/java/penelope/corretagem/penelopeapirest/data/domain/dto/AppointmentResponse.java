package penelope.corretagem.penelopeapirest.data.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import penelope.corretagem.penelopeapirest.application.dto.EstateResponse;
import penelope.corretagem.penelopeapirest.data.domain.enums.Status;

import java.time.LocalDateTime;

public record AppointmentResponse(
        Long id,
        UserResponse client,
        UserResponse estateAgent,
        EstateResponse estate,
        @JsonProperty("durationMinutes") Integer durationMinutes,
        @JsonProperty("startDateTime") LocalDateTime startDateTime,
        @JsonProperty("endDateTime") LocalDateTime endDateTime,
        Status status,
        @JsonProperty("calBookingId") Long calBookingId,
        @JsonProperty("createdAt") LocalDateTime createdAt,
        @JsonProperty("updatedAt") LocalDateTime updatedAt
) {
}
