package penelope.corretagem.penelopeapirest.application.useCase.amenities;

import penelope.corretagem.penelopeapirest.core.amenities.repository.IAmenitiesRepository;
import penelope.corretagem.penelopeapirest.application.dto.AmenitiesResponse;
import penelope.corretagem.penelopeapirest.application.dto.PaginatedAmenitiesResponse;
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

    public PaginatedAmenitiesResponse execute(Integer page, Integer pageSize) {
        int safePage = page == null ? DEFAULT_PAGE : page;
        int safePageSize = pageSize == null ? DEFAULT_PAGE_SIZE : pageSize;

        if (safePage < 1) {
            throw new DomainValidationException("O parâmetro page deve ser maior ou igual a 1");
        }

        if (safePageSize < 1) {
            throw new DomainValidationException("O parâmetro pageSize deve ser maior ou igual a 1");
        }

        int offset = (safePage - 1) * safePageSize;
        
        // Busca os itens da página
        List<AmenitiesResponse> content = amenitiesRepository.findAll(offset, safePageSize).stream()
                .map(AmenitiesResponse::fromDomain)
                .collect(Collectors.toList());
        
        // Busca o total de elementos
        long totalElements = amenitiesRepository.count();

        return PaginatedAmenitiesResponse.of(content, safePage, safePageSize, totalElements);
    }
}
