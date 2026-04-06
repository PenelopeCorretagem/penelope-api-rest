package penelope.corretagem.penelopeapirest.infrastructure.api.dto.booking;

import java.util.List;

public record BookingListResponse(
        List<BookingResponse> bookings,
        Pagination pagination
) {
}
