package penelope.corretagem.penelopeapirest.data.domain.dto;

public record EstateAgentResponse(
  long id,
  UserResponse user,
  String name,
  String creci,
  String phoneNumber,
  AddressResponse address
) {}