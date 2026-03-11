package penelope.corretagem.penelopeapirest.oldArchiteture.domain.dto.cal.booking;

public record BookingCancelRequest(
        String reason,
        Boolean allRemainingBookings
) {
}