package penelope.corretagem.penelopeapirest.application.useCase.amenities;

import org.springframework.stereotype.Service;
import penelope.corretagem.penelopeapirest.application.dto.CreateAmenityRequest;
import penelope.corretagem.penelopeapirest.core.amenities.Amenities;
import penelope.corretagem.penelopeapirest.infrastructure.adapter.AmenitiesRepositoryAdapter;

@Service
public class CreateAmenityUseCase {

    private final AmenitiesRepositoryAdapter amenitiesRepositoryAdapter;

    public CreateAmenityUseCase(AmenitiesRepositoryAdapter amenitiesRepositoryAdapter) {
        this.amenitiesRepositoryAdapter = amenitiesRepositoryAdapter;
    }

    public Amenities execute(CreateAmenityRequest request){

        Amenities newAmenity = Amenities.createNew(request.description(), request.icon());

        return amenitiesRepositoryAdapter.save(newAmenity);
    }

}
