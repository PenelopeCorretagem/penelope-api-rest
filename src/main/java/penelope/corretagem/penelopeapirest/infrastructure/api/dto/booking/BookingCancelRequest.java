package penelope.corretagem.penelopeapirest.infrastructure.api.dto.booking;

public record BookingCancelRequest(
        String reason,
        Boolean allRemainingBookings
) {
}
