package penelope.corretagem.penelopeapirest.infrastucture.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import penelope.corretagem.penelopeapirest.application.useCase.GetAdvertisementByIdUseCase;
import penelope.corretagem.penelopeapirest.core.advertisement.repository.AdvertisementRepository;

@Configuration
public class UseCaseConfig {

    @Bean
    public GetAdvertisementByIdUseCase getAdvertisementByIdUseCase(AdvertisementRepository repository) {
        return new GetAdvertisementByIdUseCase(repository);
    }
}