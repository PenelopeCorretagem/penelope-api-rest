package penelope.corretagem.penelopeapirest.data.domain.dto;

public record CityRequest(
  String name,
  String uf,
  String country
) {
}
