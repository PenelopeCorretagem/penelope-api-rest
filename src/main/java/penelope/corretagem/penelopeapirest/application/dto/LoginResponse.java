package penelope.corretagem.penelopeapirest.application.dto;

public record LoginResponse(String token, Long id, Integer accessLevel) {}
