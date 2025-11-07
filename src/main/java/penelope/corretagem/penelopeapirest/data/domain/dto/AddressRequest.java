package penelope.corretagem.penelopeapirest.data.domain.dto;

public record AddressRequest(
  String street,
  String number,
  String neighborhood,
  String complement,
  String zipCode,
  CityRequest city
) {
}
