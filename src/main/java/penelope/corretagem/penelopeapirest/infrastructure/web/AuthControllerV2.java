package penelope.corretagem.penelopeapirest.infrastructure.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import penelope.corretagem.penelopeapirest.application.dto.LoginRequest;
import penelope.corretagem.penelopeapirest.application.dto.LoginResponse;
import penelope.corretagem.penelopeapirest.application.useCase.auth.AuthenticateUserUseCase;

@RestController
@RequestMapping("/api/v2/auth")
public class AuthControllerV2 {

    private final AuthenticateUserUseCase authenticateUserUseCase;

    public AuthControllerV2(AuthenticateUserUseCase authenticateUserUseCase) {
        this.authenticateUserUseCase = authenticateUserUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        var response = authenticateUserUseCase.execute(request);
        return ResponseEntity.ok(response);
    }
}