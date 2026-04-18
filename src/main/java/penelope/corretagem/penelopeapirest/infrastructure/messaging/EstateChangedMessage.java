package penelope.corretagem.penelopeapirest.infrastructure.messaging;

public record EstateChangedMessage(
        Long estateId,
        Long advertisementId,
        String title,
        String description,
        String slug,
        String action
) {
    public static final String ACTION_CREATED = "CREATED";
    public static final String ACTION_UPDATED = "UPDATED";
}
