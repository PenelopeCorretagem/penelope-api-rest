package penelope.corretagem.penelopeapirest.core.gateway;

import penelope.corretagem.penelopeapirest.core.estate.Estate;

public interface IEstateEventPublisherGateway {

    void publishEstateCreated(Estate estate, Long advertisementId);

    void publishEstateUpdated(Estate estate, Long advertisementId);
}
