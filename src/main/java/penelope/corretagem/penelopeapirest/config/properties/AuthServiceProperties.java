package penelope.corretagem.penelopeapirest.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "auth-service")
public record AuthServiceProperties(Api api) {

    public record Api(String baseUrl) {
    }
}
