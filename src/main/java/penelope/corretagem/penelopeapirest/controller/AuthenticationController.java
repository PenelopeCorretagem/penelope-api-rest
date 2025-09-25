package penelope.corretagem.penelopeapirest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import penelope.corretagem.penelopeapirest.dto.*;
import penelope.corretagem.penelopeapirest.service.TokenService;
import penelope.corretagem.penelopeapirest.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserService userService;

    public AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {

        var usernamePassword = new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.senha());
        Authentication auth = authenticationManager.authenticate(usernamePassword);

        String token = tokenService.generateToken((UserDetails) auth.getPrincipal());

        return new LoginResponse(token);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        userService.generatePasswordResetToken(forgotPasswordRequest.email());
        return ResponseEntity.ok("Se o e-mail estiver cadastrado, um código de verificação será enviado.");
    }

    @PostMapping("/validate-token")
    public ResponseEntity<String> validateToken(@RequestBody ValidateTokenRequest request) {
        userService.validatePasswordResetToken(request.token());
        return ResponseEntity.ok("Token é válido.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        userService.resetPassword(request.token(), request.newPassword());
        return ResponseEntity.ok("Senha redefinida com sucesso.");
    }
}
