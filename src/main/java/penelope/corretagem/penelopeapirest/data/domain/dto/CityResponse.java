package penelope.corretagem.penelopeapirest.data.domain.dto;

public record CityResponse(
    String id,
    String name,
    String uf,
    String country
) {}