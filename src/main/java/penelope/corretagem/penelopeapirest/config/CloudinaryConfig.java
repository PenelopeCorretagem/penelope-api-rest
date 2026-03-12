package penelope.corretagem.penelopeapirest.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import penelope.corretagem.penelopeapirest.config.properties.CloudinaryProperties;

@Configuration
@AllArgsConstructor
public class CloudinaryConfig {

  private final CloudinaryProperties props;

  @Bean
  public Cloudinary cloudinary() {
    return new Cloudinary(
      ObjectUtils.asMap(
        "cloud_name", props.cloudName(),
        "api_key", props.apiKey(),
        "api_secret", props.apiSecret()
      )
    );
  }
}
