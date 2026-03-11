package penelope.corretagem.penelopeapirest.oldArchiteture.domain.dto.cal.booking;

import java.util.List;

public record BookingListResponse(
  List<BookingResponse> bookings,
  Pagination pagination
) {
}