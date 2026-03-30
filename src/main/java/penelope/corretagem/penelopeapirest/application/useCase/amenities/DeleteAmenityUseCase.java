package penelope.corretagem.penelopeapirest.application.useCase.amenities;

import org.springframework.stereotype.Service;
import penelope.corretagem.penelopeapirest.infrastructure.adapter.AmenitiesRepositoryAdapter;

@Service
public class DeleteAmenityUseCase {

    private final AmenitiesRepositoryAdapter amenitiesRepositoryAdapter;

    public DeleteAmenityUseCase(AmenitiesRepositoryAdapter amenitiesRepositoryAdapter) {
        this.amenitiesRepositoryAdapter = amenitiesRepositoryAdapter;
    }

    public void execute(Long id) {
        if (!amenitiesRepositoryAdapter.existsById(id)) {
            throw new RuntimeException("Diferencial nao encontrado");
        }

        amenitiesRepositoryAdapter.deleteById(id);
    }
}
