package penelope.corretagem.penelopeapirest.infrastructure.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import penelope.corretagem.penelopeapirest.infrastructure.api.CalClient;
import penelope.corretagem.penelopeapirest.core.estate.Estate;
import penelope.corretagem.penelopeapirest.core.eventType.EventType;
import penelope.corretagem.penelopeapirest.core.eventType.IEventTypeGateway;
import penelope.corretagem.penelopeapirest.data.domain.dto.cal.eventtype.EventTypeCalResponse;
import penelope.corretagem.penelopeapirest.data.domain.dto.cal.eventtype.EventTypeRequest;

@Component
public class EventTypeGatewayAdapter implements IEventTypeGateway {

    private static final Logger logger = LoggerFactory.getLogger(EventTypeGatewayAdapter.class);

    private final CalClient calClient;

    public EventTypeGatewayAdapter(CalClient calClient) {
        this.calClient = calClient;
    }

    @Override
    public EventType generateForEstate(Estate estate) {
        logger.info("Criando Event Type no Cal.com para o imóvel: {}", estate.getTitle());

        EventTypeRequest request = new EventTypeRequest(
                estate.getTitle(),
                generateSlugFromTitle(estate.getTitle()),
                60,
                estate.getDescription(),
                false,
                120,
                false
        );

        try {
            EventTypeCalResponse response = calClient.createEventType(request);

            if (response != null) {
                logger.info("Event Type criado com sucesso no Cal.com. ID: {}", response.id());

                return EventType.restore(
                        response.id(),
                        response.title(),
                        response.slug()
                );
            }
            throw new RuntimeException("A resposta do Cal.com veio nula.");

        } catch (Exception e) {
            logger.error("Erro ao criar Event Type no Cal.com para o imóvel {}: {}", estate.getTitle(), e.getMessage(), e);
            throw new RuntimeException("Falha ao criar Event Type: " + e.getMessage(), e);
        }
    }

    private String generateSlugFromTitle(String title) {
        return title.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "") // remove caracteres especiais
                .replaceAll("\\s+", "-") // substitui espaços por hífens
                .replaceAll("-+", "-") // remove hífens duplicados
                .replaceAll("^-|-$", ""); // remove hífens do início e fim
    }
}