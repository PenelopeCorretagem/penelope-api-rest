package penelope.corretagem.penelopeapirest.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import penelope.corretagem.penelopeapirest.application.useCase.GetAdvertisementByIdUseCase;
import penelope.corretagem.penelopeapirest.core.advertisement.repository.IAdvertisementRepository;

@Configuration
public class UseCaseConfig {

    @Bean
    public GetAdvertisementByIdUseCase getAdvertisementByIdUseCase(IAdvertisementRepository repository) {
        return new GetAdvertisementByIdUseCase(repository);
    }
}