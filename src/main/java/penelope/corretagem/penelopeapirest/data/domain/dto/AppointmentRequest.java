package penelope.corretagem.penelopeapirest.data.domain.dto;

public record AppointmentRequest(
  String date,
  String time,
  EstateAgentRequest estateAgent,
  EstateRequest estate,
  UserRequest user
) {}