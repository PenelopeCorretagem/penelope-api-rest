package penelope.corretagem.penelopeapirest.application.useCase.amenities;

import org.springframework.cache.annotation.Cacheable;
import penelope.corretagem.penelopeapirest.core.amenities.repository.IAmenitiesRepository;
import penelope.corretagem.penelopeapirest.application.dto.AmenitiesResponse;
import penelope.corretagem.penelopeapirest.application.dto.PaginatedAmenitiesResponse;
import penelope.corretagem.penelopeapirest.config.CacheNames;
import penelope.corretagem.penelopeapirest.core.exception.DomainValidationException;

import java.util.List;
import java.util.stream.Collectors;

public class GetAllAmenitiesUseCase {

    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_PAGE_SIZE = 10;

    private final IAmenitiesRepository amenitiesRepository;

    public GetAllAmenitiesUseCase(IAmenitiesRepository amenitiesRepository) {
        this.amenitiesRepository = amenitiesRepository;
    }

    @Cacheable(value = CacheNames.AMENITIES, key = "{ #page == null ? 1 : #page, #pageSize == null ? 10 : #pageSize, #name, #initial, #sort }")
    public PaginatedAmenitiesResponse execute(Integer page, Integer pageSize, String name, String initial, String sort) {
        int safePage = page == null ? DEFAULT_PAGE : page;
        int safePageSize = pageSize == null ? DEFAULT_PAGE_SIZE : pageSize;

        if (safePage < 1) {
            throw new DomainValidationException("O parâmetro page deve ser maior ou igual a 1");
        }

        if (safePageSize < 1) {
            throw new DomainValidationException("O parâmetro pageSize deve ser maior ou igual a 1");
        }

        int offset = (safePage - 1) * safePageSize;

        List<AmenitiesResponse> content = amenitiesRepository.findAll(offset, safePageSize, name, initial, sort).stream()
                .map(AmenitiesResponse::fromDomain)
                .collect(Collectors.toList());
        
        long totalElements = amenitiesRepository.count(name, initial);

        return PaginatedAmenitiesResponse.of(content, safePage, safePageSize, totalElements);
    }
}
