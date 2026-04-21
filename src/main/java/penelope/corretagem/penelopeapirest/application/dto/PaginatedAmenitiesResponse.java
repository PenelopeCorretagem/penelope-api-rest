package penelope.corretagem.penelopeapirest.application.dto;

import java.util.List;

public record PaginatedAmenitiesResponse(
        List<AmenitiesResponse> content,
        PageableInfo pageable
) {
    public record PageableInfo(
            Integer pageNumber,
            Integer pageSize,
            Long totalElements,
            Integer totalPages
    ) {
    }

    public static PaginatedAmenitiesResponse of(
            List<AmenitiesResponse> content,
            int pageNumber,
            int pageSize,
            long totalElements) {

        int totalPages = (int) Math.ceil((double) totalElements / pageSize);

        return new PaginatedAmenitiesResponse(
                content,
                new PageableInfo(pageNumber, pageSize, totalElements, totalPages)
        );
    }
}
