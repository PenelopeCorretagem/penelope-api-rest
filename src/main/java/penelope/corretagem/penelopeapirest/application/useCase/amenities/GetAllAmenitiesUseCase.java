package penelope.corretagem.penelopeapirest.application.useCase.amenities;

import org.springframework.stereotype.Service;
import penelope.corretagem.penelopeapirest.core.amenities.repository.IAmenitiesRepository;
import penelope.corretagem.penelopeapirest.application.dto.AmenitiesResponse;
import penelope.corretagem.penelopeapirest.infrastructure.adapter.AmenitiesRepositoryAdapter;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetAllAmenitiesUseCase {

    private final AmenitiesRepositoryAdapter amenitiesRepository;

    public GetAllAmenitiesUseCase(AmenitiesRepositoryAdapter amenitiesRepository) {
        this.amenitiesRepository = amenitiesRepository;
    }

    public List<AmenitiesResponse> execute() {
        return amenitiesRepository.findAll().stream()
                .map(AmenitiesResponse::fromDomain)
                .collect(Collectors.toList());
    }
}
