package penelope.corretagem.penelopeapirest.application.useCase.amenities;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import penelope.corretagem.penelopeapirest.config.CacheNames;
import penelope.corretagem.penelopeapirest.core.amenities.repository.IAmenitiesRepository;
import penelope.corretagem.penelopeapirest.core.exception.DomainValidationException;
import penelope.corretagem.penelopeapirest.core.exception.ResourceNotFoundException;

public class DeleteAmenityUseCase {

    private final IAmenitiesRepository amenitiesRepository;

    public DeleteAmenityUseCase(IAmenitiesRepository amenitiesRepository) {
        this.amenitiesRepository = amenitiesRepository;
    }

    @Caching(evict = {
            @CacheEvict(value = CacheNames.AMENITY, key = "#id"),
            @CacheEvict(value = CacheNames.AMENITIES, allEntries = true)
    })
    public void execute(Long id) {
        if (id == null || id <= 0) {
            throw new DomainValidationException("ID da amenidade inválido");
        }

        if (!amenitiesRepository.existsById(id)) {
            throw new ResourceNotFoundException("Diferencial não encontrado");
        }

        amenitiesRepository.deleteById(id);
    }
}
