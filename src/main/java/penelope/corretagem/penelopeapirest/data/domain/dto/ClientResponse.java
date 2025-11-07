package penelope.corretagem.penelopeapirest.data.domain.dto;

public record ClientResponse(
  Long id,
  UserResponse user,
  String name,
  String cpf,
  String phoneNumber,
  AddressResponse address
) {}