package penelope.corretagem.penelopeapirest.data.domain.dto;

public record EstateAgentRequest(
  UserRequest user,
  String name,
  String creci,
  String phoneNumber,
  AddressRequest address
) {}