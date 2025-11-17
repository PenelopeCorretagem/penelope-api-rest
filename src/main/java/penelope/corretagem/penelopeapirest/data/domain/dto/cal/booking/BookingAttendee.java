package penelope.corretagem.penelopeapirest.data.domain.dto.cal.booking;

public record BookingAttendee(
        String name,
        String email,
        String phoneNumber,
        String timeZone
) {
}