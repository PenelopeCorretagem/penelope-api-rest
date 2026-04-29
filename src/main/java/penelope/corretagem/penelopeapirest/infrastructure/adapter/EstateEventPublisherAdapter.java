package penelope.corretagem.penelopeapirest.infrastructure.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import penelope.corretagem.penelopeapirest.core.estate.Estate;
import penelope.corretagem.penelopeapirest.core.gateway.IEstateEventPublisherGateway;
import penelope.corretagem.penelopeapirest.infrastructure.messaging.EstateChangedMessage;

import java.time.Instant;

@Component
public class EstateEventPublisherAdapter implements IEstateEventPublisherGateway {

    private static final Logger logger = LoggerFactory.getLogger(EstateEventPublisherAdapter.class);
    private static final String ROUTING_KEY_ESTATE_CHANGED = "estate.changed";

    private final RabbitTemplate rabbitTemplate;
    private final String exchangeName;

    public EstateEventPublisherAdapter(
            RabbitTemplate rabbitTemplate,
            @Value("${rabbitmq.exchanges.estate:estate.exchange}") String exchangeName) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchangeName = exchangeName;
    }

    @Override
    public void publishEstateCreated(Estate estate, Long advertisementId) {
        var message = new EstateChangedMessage(
                estate.getId(),
                advertisementId,
                estate.getTitle(),
                estate.getDescription(),
                generateSlug(estate.getTitle()),
                EstateChangedMessage.ACTION_CREATED,
                EstateChangedMessage.STATUS_ACTIVE,
                Instant.now()
        );

        publish(message);
    }

    @Override
    public void publishEstateUpdated(Estate estate, Long advertisementId) {
        var message = new EstateChangedMessage(
                estate.getId(),
                advertisementId,
                estate.getTitle(),
                estate.getDescription(),
                generateSlug(estate.getTitle()),
                EstateChangedMessage.ACTION_UPDATED,
                EstateChangedMessage.STATUS_ACTIVE,
                Instant.now()
        );

        publish(message);
    }

    private void publish(EstateChangedMessage message) {
        try {
            rabbitTemplate.convertAndSend(exchangeName, ROUTING_KEY_ESTATE_CHANGED, message);
            logger.info("Evento estate.changed [{}] publicado — anúncio ID: {}, imóvel ID: {}",
                    message.action(), message.advertisementId(), message.estateId());
        } catch (Exception e) {
            logger.error("Falha ao publicar evento estate.changed [{}] para anúncio ID: {} — {}",
                    message.action(), message.advertisementId(), e.getMessage(), e);
        }
    }

    private String generateSlug(String title) {
        return title.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("-+", "-")
                .replaceAll("^-|-$", "");
    }
}
