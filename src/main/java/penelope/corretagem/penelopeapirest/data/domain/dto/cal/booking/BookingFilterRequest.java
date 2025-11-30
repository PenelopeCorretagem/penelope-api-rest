package penelope.corretagem.penelopeapirest.data.domain.dto.cal.booking;

import java.time.LocalDate;

public record BookingFilterRequest(
        Long eventTypeId,
        Long userId,
        LocalDate dateFrom,
        LocalDate dateTo,
        Integer page,
        Integer size
){ }
