package penelope.corretagem.penelopeapirest.infrastructure.web;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import penelope.corretagem.penelopeapirest.application.dto.LoginRequest;
import penelope.corretagem.penelopeapirest.application.dto.LoginResponse;
import penelope.corretagem.penelopeapirest.application.dto.ResetPasswordRequest;
import penelope.corretagem.penelopeapirest.application.dto.ValidateTokenRequest;
import penelope.corretagem.penelopeapirest.application.useCase.auth.AuthenticateUserUseCase;
import penelope.corretagem.penelopeapirest.application.useCase.user.ResetPasswordUseCase;
import penelope.corretagem.penelopeapirest.application.useCase.user.ValidatePasswordResetTokenUseCase;

import java.util.Map;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final AuthenticateUserUseCase authenticateUserUseCase;
    private final ValidatePasswordResetTokenUseCase validatePasswordResetTokenUseCase;
    private final ResetPasswordUseCase resetPasswordUseCase;

    public AuthController(AuthenticateUserUseCase authenticateUserUseCase,
                          ValidatePasswordResetTokenUseCase validatePasswordResetTokenUseCase,
                          ResetPasswordUseCase resetPasswordUseCase) {
        this.authenticateUserUseCase = authenticateUserUseCase;
        this.validatePasswordResetTokenUseCase = validatePasswordResetTokenUseCase;
        this.resetPasswordUseCase = resetPasswordUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        var response = authenticateUserUseCase.execute(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/validate-reset-token")
    public ResponseEntity<Map<String, String>> validateToken(@Valid @RequestBody ValidateTokenRequest request) {

        validatePasswordResetTokenUseCase.execute(request.token());

        return ResponseEntity.ok(Map.of("message", "Token válido."));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody ResetPasswordRequest request) {

        resetPasswordUseCase.execute(request.token(), request.newPassword());

        return ResponseEntity.ok(Map.of("message", "Senha redefinida com sucesso!"));
    }
}