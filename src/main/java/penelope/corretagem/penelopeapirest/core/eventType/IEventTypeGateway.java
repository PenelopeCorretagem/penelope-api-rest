package penelope.corretagem.penelopeapirest.core.eventType;

import penelope.corretagem.penelopeapirest.core.estate.Estate;

public interface IEventTypeGateway {

    EventType generateForEstate(Estate estate);
}
