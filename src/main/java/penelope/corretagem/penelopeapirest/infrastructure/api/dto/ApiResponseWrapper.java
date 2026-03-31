package penelope.corretagem.penelopeapirest.infrastructure.api.dto;

import penelope.corretagem.penelopeapirest.infrastructure.api.dto.booking.Pagination;

public record ApiResponseWrapper<T>(
        String status,
        T data,
        Pagination pagination,
        Object error
) {
}
