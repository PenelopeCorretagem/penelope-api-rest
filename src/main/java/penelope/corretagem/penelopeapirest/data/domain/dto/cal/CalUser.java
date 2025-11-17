package penelope.corretagem.penelopeapirest.data.domain.dto.cal;

public record CalUser(
  Long id,
  String username,
  String email,
  String name
) {}