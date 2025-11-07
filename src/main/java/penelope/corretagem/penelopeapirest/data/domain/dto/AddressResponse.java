package penelope.corretagem.penelopeapirest.data.domain.dto;

public record AddressResponse(
  String id,
  String street,
  String number,
  String neighborhood,
  String complement,
  String zipCode,
  CityResponse city
) {}