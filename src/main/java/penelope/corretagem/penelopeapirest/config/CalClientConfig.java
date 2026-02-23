package penelope.corretagem.penelopeapirest.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import penelope.corretagem.penelopeapirest.config.properties.CalcomProperties;

@Configuration
public class CalClientConfig {

  private final CalcomProperties prop;

  public CalClientConfig(CalcomProperties prop) {
    this.prop = prop;
  }

  @Bean
  @Qualifier("calRestClientV1")
  public RestClient calRestClientV1(RestClient.Builder builder) {
    return builder
      .baseUrl(prop.api().baseUrl())
      .defaultHeader("Authorization", "Bearer " + prop.api().key())
      .defaultHeader("cal-api-version", prop.api().versionV1())  // Versão para EventType
      .build();
  }

  @Bean
  @Qualifier("calRestClientV2")
  public RestClient calRestClientV2(RestClient.Builder builder) {
    return builder
      .baseUrl(prop.api().baseUrl())
      .defaultHeader("Authorization", "Bearer " + prop.api().key())
      .defaultHeader("cal-api-version", prop.api().versionV2())  // Versão para Bookings
      .build();
  }
}