package penelope.corretagem.penelopeapirest.infrastructure.api.dto.booking;

public record BookingAttendee(
        String name,
        String email,
        String phoneNumber,
        String timeZone
) {
}
