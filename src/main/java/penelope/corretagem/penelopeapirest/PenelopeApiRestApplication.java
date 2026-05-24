package penelope.corretagem.penelopeapirest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import penelope.corretagem.penelopeapirest.config.properties.AuthServiceProperties;

import penelope.corretagem.penelopeapirest.config.properties.CloudinaryProperties;
import penelope.corretagem.penelopeapirest.config.properties.CorsProperties;

@SpringBootApplication(scanBasePackages = "penelope.corretagem.penelopeapirest")
@EnableConfigurationProperties({
  AuthServiceProperties.class,
  CloudinaryProperties.class,
  CorsProperties.class
})
public class PenelopeApiRestApplication {

  public static void main(String[] args) {
    SpringApplication.run(PenelopeApiRestApplication.class, args);
  }

}
