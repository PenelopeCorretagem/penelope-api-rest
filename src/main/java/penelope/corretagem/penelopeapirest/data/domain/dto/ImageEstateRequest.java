package penelope.corretagem.penelopeapirest.data.domain.dto;

public record ImageEstateRequest(
  String url,
  EstateRequest estate
) {}