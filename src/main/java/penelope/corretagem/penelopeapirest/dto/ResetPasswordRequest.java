package penelope.corretagem.penelopeapirest.dto;

public record ResetPasswordRequest(String token, String newPassword) {}
