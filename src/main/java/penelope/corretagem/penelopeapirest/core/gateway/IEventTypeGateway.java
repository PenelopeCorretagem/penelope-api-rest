package penelope.corretagem.penelopeapirest.core.gateway;

import penelope.corretagem.penelopeapirest.core.estate.Estate;
import penelope.corretagem.penelopeapirest.core.eventType.EventType;

public interface IEventTypeGateway {
    EventType generateForEstate(Estate estate);

    EventType recreateForEstate(EventType currentEventType, Estate newEstate);
}
