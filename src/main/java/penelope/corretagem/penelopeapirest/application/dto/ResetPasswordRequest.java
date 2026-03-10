package penelope.corretagem.penelopeapirest.application.dto;

public record ResetPasswordRequest(String token, String newPassword) {}
