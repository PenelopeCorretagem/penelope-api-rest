package penelope.corretagem.penelopeapirest.oldArchiteture.domain.dto;

import penelope.corretagem.penelopeapirest.oldArchiteture.domain.dto.cal.booking.Pagination;

public record ApiResponseWrapper<T> (
  String status,
  T data,
  Pagination pagination,
  Object error
) {}