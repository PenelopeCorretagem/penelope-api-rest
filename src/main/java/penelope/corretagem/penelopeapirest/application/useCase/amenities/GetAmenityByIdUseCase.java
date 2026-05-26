package penelope.corretagem.penelopeapirest.application.useCase.amenities;

import org.springframework.cache.annotation.Cacheable;
import penelope.corretagem.penelopeapirest.application.dto.AmenitiesResponse;
import penelope.corretagem.penelopeapirest.config.CacheNames;
import penelope.corretagem.penelopeapirest.core.amenities.repository.IAmenitiesRepository;
import penelope.corretagem.penelopeapirest.core.exception.DomainValidationException;
import penelope.corretagem.penelopeapirest.core.exception.ResourceNotFoundException;

public class GetAmenityByIdUseCase {

    private final IAmenitiesRepository repository;

    public GetAmenityByIdUseCase(IAmenitiesRepository repository) {
        this.repository = repository;
    }

    @Cacheable(value = CacheNames.AMENITY, key = "#id")
    public AmenitiesResponse execute(Long id) {
        if (id == null || id <= 0) {
            throw new DomainValidationException("ID da amenidade inválido");
        }

        var amenity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Amenidade não encontrada"));

        return AmenitiesResponse.fromDomain(amenity);
    }
}
