package penelope.corretagem.penelopeapirest.data.domain.dto;

import penelope.corretagem.penelopeapirest.data.domain.dto.cal.booking.Pagination;

public record ApiResponseWrapper<T> (
  String status,
  T data,
  Pagination pagination,
  Object error
) {}