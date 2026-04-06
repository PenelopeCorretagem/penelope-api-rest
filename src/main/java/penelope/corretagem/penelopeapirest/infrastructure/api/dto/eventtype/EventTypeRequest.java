package penelope.corretagem.penelopeapirest.infrastructure.api.dto.eventtype;

public record EventTypeRequest(
        String title,
        String slug,
        Integer lengthInMinutes,
        String description,
        Boolean hidden,
        Integer minimumBookingNotice,
        Boolean requiresConfirmation
) {
}
