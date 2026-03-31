package penelope.corretagem.penelopeapirest.application.useCase.amenities;

import penelope.corretagem.penelopeapirest.core.amenities.repository.IAmenitiesRepository;
import penelope.corretagem.penelopeapirest.application.dto.AmenitiesResponse;

import java.util.List;
import java.util.stream.Collectors;

public class GetAllAmenitiesUseCase {

    private final IAmenitiesRepository amenitiesRepository;

    public GetAllAmenitiesUseCase(IAmenitiesRepository amenitiesRepository) {
        this.amenitiesRepository = amenitiesRepository;
    }

    public List<AmenitiesResponse> execute() {
        return amenitiesRepository.findAll().stream()
                .map(AmenitiesResponse::fromDomain)
                .collect(Collectors.toList());
    }
}
