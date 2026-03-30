package penelope.corretagem.penelopeapirest.application.useCase.amenities;

import org.springframework.stereotype.Service;
import penelope.corretagem.penelopeapirest.application.dto.UpdateAmenityRequest;
import penelope.corretagem.penelopeapirest.core.amenities.Amenities;
import penelope.corretagem.penelopeapirest.core.amenities.repository.IAmenitiesRepository;

@Service
public class UpdateAmenityUseCase {

    private final IAmenitiesRepository repository;

    public UpdateAmenityUseCase(IAmenitiesRepository repository) {
        this.repository = repository;
    }

    public Amenities execute(Long id, UpdateAmenityRequest request) {
        Amenities amenity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Amenidade não encontrada"));

        amenity.updateInfo(request.description(), request.icon());

        return repository.save(amenity);
    }
}