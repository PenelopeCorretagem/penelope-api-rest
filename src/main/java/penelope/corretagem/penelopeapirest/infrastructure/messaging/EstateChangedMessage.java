package penelope.corretagem.penelopeapirest.infrastructure.messaging;

import java.time.Instant;

public record EstateChangedMessage(
        Long estateId,
        Long advertisementId,
        String title,
        String description,
        String slug,
        String action,
        String newStatus,
        Instant occurredAt
) {
    public static final String ACTION_CREATED = "CREATED";
    public static final String ACTION_UPDATED = "UPDATED";
    public static final String STATUS_ACTIVE = "ACTIVE";
    public static final String STATUS_INACTIVE = "INACTIVE";
}
