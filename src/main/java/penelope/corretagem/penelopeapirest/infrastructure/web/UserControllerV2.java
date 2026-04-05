package penelope.corretagem.penelopeapirest.infrastructure.web;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import penelope.corretagem.penelopeapirest.application.dto.ForgotPasswordRequest;
import penelope.corretagem.penelopeapirest.application.dto.UserAuthInfoResponse;
import penelope.corretagem.penelopeapirest.application.useCase.user.*;
import penelope.corretagem.penelopeapirest.application.dto.UserRequest;
import penelope.corretagem.penelopeapirest.application.dto.UserResponse;
import penelope.corretagem.penelopeapirest.application.dto.UserUpdateRequest;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserControllerV2 {

    private final GetAllUsersUseCase getAllUsersUseCase;
    private final GetUserAuthInfoUseCase getUserAuthInfoUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final GeneratePasswordResetTokenUseCase generatePasswordResetTokenUseCase;

    public UserControllerV2(GetAllUsersUseCase getAllUsersUseCase, GetUserAuthInfoUseCase getUserAuthInfoUseCase, GetUserByIdUseCase getUserByIdUseCase, CreateUserUseCase createUserUseCase, UpdateUserUseCase updateUserUseCase, DeleteUserUseCase deleteUserUseCase, GeneratePasswordResetTokenUseCase generatePasswordResetTokenUseCase, ResetPasswordUseCase resetPasswordUseCase) {
        this.getAllUsersUseCase = getAllUsersUseCase;
        this.getUserAuthInfoUseCase = getUserAuthInfoUseCase;
        this.getUserByIdUseCase = getUserByIdUseCase;
        this.createUserUseCase = createUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
        this.generatePasswordResetTokenUseCase = generatePasswordResetTokenUseCase;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll() {

        var response = getAllUsersUseCase.execute();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {

        var response = getUserByIdUseCase.execute(id);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody @Valid UserRequest request) {
        var response = createUserUseCase.execute(request);
        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @RequestBody UserUpdateRequest request) {
        var response = updateUserUseCase.execute(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteUserUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        generatePasswordResetTokenUseCase.execute(request.email());
        var responseBody = Map.of("message", "Se o e-mail estiver cadastrado, um código de verificação será enviado.");
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserAuthInfoResponse> getUserAuthInfo(@RequestHeader("Authorization") String tokenHeader) {

        String rawToken = tokenHeader.replace("Bearer ", "");
        var response = getUserAuthInfoUseCase.execute(rawToken);

        return ResponseEntity.ok(response);
    }
}
