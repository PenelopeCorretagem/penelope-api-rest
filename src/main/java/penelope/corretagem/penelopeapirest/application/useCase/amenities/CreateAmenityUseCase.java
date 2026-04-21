package penelope.corretagem.penelopeapirest.application.useCase.amenities;

import penelope.corretagem.penelopeapirest.application.dto.CreateAmenityRequest;
import penelope.corretagem.penelopeapirest.core.amenities.Amenities;
import penelope.corretagem.penelopeapirest.core.amenities.repository.IAmenitiesRepository;
import penelope.corretagem.penelopeapirest.core.exception.DomainValidationException;

public class CreateAmenityUseCase {

    private final IAmenitiesRepository amenitiesRepository;

    public CreateAmenityUseCase(IAmenitiesRepository amenitiesRepository) {
        this.amenitiesRepository = amenitiesRepository;
    }

    public Amenities execute(CreateAmenityRequest request){
        if (request == null) {
            throw new DomainValidationException("Requisição de criação da amenidade é obrigatória");
        }

        if (request.description() == null || request.description().isBlank()) {
            throw new DomainValidationException("Descrição da amenidade é obrigatória");
        }

        // Verifica se já existe uma amenity com a mesma descrição
        if (amenitiesRepository.findByDescription(request.description()).isPresent()) {
            throw new DomainValidationException("Já existe um diferencial com a descrição '" + request.description() + "'");
        }

        Amenities newAmenity = Amenities.createNew(request.description(), request.icon());

        return amenitiesRepository.save(newAmenity);
    }

}
