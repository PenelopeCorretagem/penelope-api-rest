package penelope.corretagem.penelopeapirest.infrastructure.mapper;

import org.springframework.stereotype.Component;
import penelope.corretagem.penelopeapirest.core.eventType.EventType;
import penelope.corretagem.penelopeapirest.infrastructure.entity.EventTypeJpaEntity;

@Component
public class EventTypeInfrastructureMapper {

    public EventType toDomain(EventTypeJpaEntity jpa) {
        if (jpa == null) return null;

        return EventType.restore(
                jpa.getId(),
                jpa.getTitle(),
                jpa.getSlug()
        );
    }

    public EventTypeJpaEntity toEntity(EventType domain) {
        if (domain == null) return null;

        var entity = new EventTypeJpaEntity();
        entity.setId(domain.getId());
        entity.setTitle(domain.getTitle());
        entity.setSlug(domain.getSlug());

        return entity;
    }
}
