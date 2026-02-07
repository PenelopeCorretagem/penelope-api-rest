package penelope.corretagem.penelopeapirest.data.domain.dto;

public record ResetPasswordRequest(String token, String newPassword) {}
