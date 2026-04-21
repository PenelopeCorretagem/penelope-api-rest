package penelope.corretagem.penelopeapirest.application.useCase.amenities;

import penelope.corretagem.penelopeapirest.application.dto.UpdateAmenityRequest;
import penelope.corretagem.penelopeapirest.core.amenities.Amenities;
import penelope.corretagem.penelopeapirest.core.amenities.repository.IAmenitiesRepository;
import penelope.corretagem.penelopeapirest.core.exception.DomainValidationException;
import penelope.corretagem.penelopeapirest.core.exception.ResourceNotFoundException;

public class UpdateAmenityUseCase {

    private final IAmenitiesRepository repository;

    public UpdateAmenityUseCase(IAmenitiesRepository repository) {
        this.repository = repository;
    }

    public Amenities execute(Long id, UpdateAmenityRequest request) {
        if (id == null || id <= 0) {
            throw new DomainValidationException("ID do diferencial inválido");
        }

        if (request == null) {
            throw new DomainValidationException("Requisição de atualização de diferencial é obrigatória");
        }

        Amenities amenity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Diferencial não encontrado"));

        // Se está mudando a descrição, valida se já existe outra com essa descrição
        if (request.description() != null && !request.description().isBlank() 
            && !request.description().equals(amenity.getDescription())) {
            if (repository.findByDescription(request.description()).isPresent()) {
                throw new DomainValidationException("Já existe um diferencial com a descrição '" + request.description() + "'");
            }
        }

        amenity.updateInfo(request.description(), request.icon());

        return repository.save(amenity);
    }
}