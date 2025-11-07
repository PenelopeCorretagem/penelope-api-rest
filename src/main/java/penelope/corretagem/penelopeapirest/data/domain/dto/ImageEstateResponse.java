package penelope.corretagem.penelopeapirest.data.domain.dto;

public record ImageEstateResponse(
  long id,
  String url,
  EstateResponse estate
) {}