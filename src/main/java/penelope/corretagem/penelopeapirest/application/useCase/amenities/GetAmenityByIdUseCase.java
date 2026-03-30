package penelope.corretagem.penelopeapirest.application.useCase.amenities;

import org.springframework.stereotype.Service;
import penelope.corretagem.penelopeapirest.application.dto.AmenitiesResponse;
import penelope.corretagem.penelopeapirest.infrastructure.adapter.AmenitiesRepositoryAdapter;

@Service
public class GetAmenityByIdUseCase {

    private final AmenitiesRepositoryAdapter repository;

    public GetAmenityByIdUseCase(AmenitiesRepositoryAdapter repository) {
        this.repository = repository;
    }

    public AmenitiesResponse execute(Long id) {

        var amenity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Amenidade não encontrada"));

        return AmenitiesResponse.fromDomain(amenity);
    }
}
