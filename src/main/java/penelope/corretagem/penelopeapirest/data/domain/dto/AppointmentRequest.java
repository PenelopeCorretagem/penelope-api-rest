package penelope.corretagem.penelopeapirest.data.domain.dto;

import java.time.LocalDate;

public record AppointmentRequest(
        Long id,
        UserRequest client,
        UserRequest estateAgent,
        EstateRequest estate,
        String durationMinutes,
        LocalDate dateAppointment,
        String status
) {
}