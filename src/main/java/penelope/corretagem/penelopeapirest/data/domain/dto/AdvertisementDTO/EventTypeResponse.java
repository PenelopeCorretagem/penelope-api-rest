package penelope.corretagem.penelopeapirest.data.domain.dto.AdvertisementDTO;

import penelope.corretagem.penelopeapirest.core.eventType.EventType;

public record EventTypeResponse(
        Long id,
        String title,
        String slug
) {
    public static EventTypeResponse fromDomain(EventType eventType) {
        if(eventType == null) return null;

        return new EventTypeResponse(
                eventType.getId(),
                eventType.getTitle(),
                eventType.getSlug()
        );
    }
}
