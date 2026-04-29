package penelope.corretagem.penelopeapirest.application.dto;

import jakarta.validation.constraints.NotBlank;

public record ValidateTokenRequest(
        @NotBlank(message = "O token de redefinição não pode estar vazio")
        String token
) {
}