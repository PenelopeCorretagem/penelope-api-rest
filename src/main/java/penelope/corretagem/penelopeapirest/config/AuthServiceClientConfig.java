package penelope.corretagem.penelopeapirest.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClient;
import penelope.corretagem.penelopeapirest.config.properties.AuthServiceProperties;

@Configuration
public class AuthServiceClientConfig {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceClientConfig.class);
    private static final String LOCAL_FALLBACK_BASE_URL = "http://localhost:9000";

    private final AuthServiceProperties properties;
    private final Environment environment;

    public AuthServiceClientConfig(AuthServiceProperties properties, Environment environment) {
        this.properties = properties;
        this.environment = environment;
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
            if (environment.acceptsProfiles(Profiles.of("dev", "test"))) {
                log.warn("Property auth-service.api.base-url is not configured. Using local fallback {}", LOCAL_FALLBACK_BASE_URL);
                return LOCAL_FALLBACK_BASE_URL;
            }

            throw new IllegalStateException("Property auth-service.api.base-url must be configured for non-dev/test environments");
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
