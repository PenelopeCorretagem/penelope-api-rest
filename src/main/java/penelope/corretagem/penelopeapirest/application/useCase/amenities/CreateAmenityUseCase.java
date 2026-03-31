package penelope.corretagem.penelopeapirest.application.useCase.amenities;

import org.springframework.stereotype.Service;
import penelope.corretagem.penelopeapirest.application.dto.CreateAmenityRequest;
import penelope.corretagem.penelopeapirest.core.amenities.Amenities;
import penelope.corretagem.penelopeapirest.core.amenities.repository.IAmenitiesRepository;
import penelope.corretagem.penelopeapirest.core.exception.DomainValidationException;

@Service
public class CreateAmenityUseCase {

    private final IAmenitiesRepository amenitiesRepository;

    public CreateAmenityUseCase(IAmenitiesRepository amenitiesRepository) {
        this.amenitiesRepository = amenitiesRepository;
    }

    public Amenities execute(CreateAmenityRequest request){
        if (request == null) {
            throw new DomainValidationException("Requisição de criação da amenidade é obrigatória");
        }

        Amenities newAmenity = Amenities.createNew(request.description(), request.icon());

        return amenitiesRepository.save(newAmenity);
    }

}
