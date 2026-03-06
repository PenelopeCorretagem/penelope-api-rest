package penelope.corretagem.penelopeapirest.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "calcom")
public record CalcomProperties(Api api, Webhook webhook) {
  public record Api(String baseUrl, String key, String versionV1, String versionV2) {}
  public record Webhook(String secret) {}
}