package penelope.corretagem.penelopeapirest.data.domain.dto;

public record ClientRequest(
  UserRequest user,
  String name,
  String cpf,
  String phoneNumber,
  AddressRequest address
) {}