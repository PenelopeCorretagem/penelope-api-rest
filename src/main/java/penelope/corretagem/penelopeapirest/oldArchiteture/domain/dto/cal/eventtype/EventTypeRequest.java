package penelope.corretagem.penelopeapirest.oldArchiteture.domain.dto.cal.eventtype;

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