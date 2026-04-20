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
        String normalizedBaseUrl = normalizeBaseUrl(properties.api().baseUrl());

        return builder
            .baseUrl(normalizedBaseUrl)
            .build();
    }

    private String normalizeBaseUrl(String configuredBaseUrl) {
        if (configuredBaseUrl == null || configuredBaseUrl.isBlank()) {
            return "http://localhost:9000";
        }

        String trimmed = configuredBaseUrl.trim();

        if (trimmed.endsWith("/api")) {
            return trimmed.substring(0, trimmed.length() - 4);
        }

        if (trimmed.endsWith("/api/")) {
            return trimmed.substring(0, trimmed.length() - 5);
        }

        if (trimmed.endsWith("/")) {
            return trimmed.substring(0, trimmed.length() - 1);
        }

        return trimmed;
    }
}
