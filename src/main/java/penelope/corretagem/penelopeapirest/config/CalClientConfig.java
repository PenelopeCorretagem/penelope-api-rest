package penelope.corretagem.penelopeapirest.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class CalClientConfig {

  @Value("${calcom.api.base-url}")
  private String baseUrl;

  @Value("${calcom.api.key}")
  private String apiKey;

  @Bean
  @Qualifier("calRestClientV1")
  public RestClient calRestClientV1(RestClient.Builder builder) {
    return builder
      .baseUrl(baseUrl)
      .defaultHeader("Authorization", "Bearer " + apiKey)
      .defaultHeader("cal-api-version", "2024-06-14")  // Versão para EventType
      .build();
  }

  @Bean
  @Qualifier("calRestClientV2")
  public RestClient calRestClientV2(RestClient.Builder builder) {
    return builder
      .baseUrl(baseUrl)
      .defaultHeader("Authorization", "Bearer " + apiKey)
      .defaultHeader("cal-api-version", "2024-08-13")  // Versão para Bookings
      .build();
  }
}