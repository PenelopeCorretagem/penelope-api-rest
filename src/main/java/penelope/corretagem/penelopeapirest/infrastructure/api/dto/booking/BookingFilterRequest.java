package penelope.corretagem.penelopeapirest.infrastructure.api.dto.booking;

import java.time.LocalDate;

public record BookingFilterRequest(
        Long eventTypeId,
        Long userId,
        LocalDate dateFrom,
        LocalDate dateTo,
        Integer page,
        Integer size
) {
}
