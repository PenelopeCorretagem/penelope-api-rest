package penelope.corretagem.penelopeapirest.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

  public static final String EXCHANGE_NAME = "penelope.domain.events";

  @Bean
  public MessageConverter jsonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public org.springframework.amqp.core.TopicExchange domainExchange() {
    return new org.springframework.amqp.core.TopicExchange(EXCHANGE_NAME);
  }
}
