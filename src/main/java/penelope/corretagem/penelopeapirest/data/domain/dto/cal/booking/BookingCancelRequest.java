package penelope.corretagem.penelopeapirest.data.domain.dto.cal.booking;

public record BookingCancelRequest(
        String reason,
        Boolean allRemainingBookings
) {
}