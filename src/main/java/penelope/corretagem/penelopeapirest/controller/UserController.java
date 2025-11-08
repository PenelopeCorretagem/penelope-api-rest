package penelope.corretagem.penelopeapirest.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import penelope.corretagem.penelopeapirest.data.domain.dto.*;
import penelope.corretagem.penelopeapirest.service.UserService;

import java.util.List;

@Tag(name = "Usuários")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Cria um novo usuário com os dados fornecidos.
    @PostMapping
    public ResponseEntity<UserResponse> addUser(@Valid @RequestBody UserRequest userRequest) {
        UserResponse response = userService.addUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Retorna a lista de todos os usuários cadastrados.
    @GetMapping
    public ResponseEntity<List<UserResponse>> showAllUsers() {
        List<UserResponse> users = userService.showAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable @NotNull Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // Atualiza os dados de um usuário específico pelo ID.
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id,
                                                   @Valid @RequestBody UserRequest userRequestUpdate) {
        UserResponse response = userService.updateUser(id, userRequestUpdate);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponse> deleteClient(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
