package penelope.corretagem.penelopeapirest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(scanBasePackages = "penelope.corretagem.penelopeapirest")
public class PenelopeApiRestApplication {

  public static void main(String[] args) {
    SpringApplication.run(PenelopeApiRestApplication.class, args);
  }

}
