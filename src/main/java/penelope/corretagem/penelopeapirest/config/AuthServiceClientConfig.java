package penelope.corretagem.penelopeapirest.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import penelope.corretagem.penelopeapirest.config.properties.AuthServiceProperties;

@Configuration
public class AuthServiceClientConfig {

    private final AuthServiceProperties properties;

    public AuthServiceClientConfig(AuthServiceProperties properties) {
        this.properties = properties;
    }

    @Bean
    @Qualifier("authServiceRestClient")
    public RestClient authServiceRestClient(RestClient.Builder builder) {
        return builder
            .baseUrl(properties.api().baseUrl())
            .build();
    }
}
