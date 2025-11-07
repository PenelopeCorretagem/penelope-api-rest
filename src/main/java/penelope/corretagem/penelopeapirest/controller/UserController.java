package penelope.corretagem.penelopeapirest.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import penelope.corretagem.penelopeapirest.data.domain.dto.EstateAgentRequest;
import penelope.corretagem.penelopeapirest.data.domain.dto.EstateAgentResponse;
import penelope.corretagem.penelopeapirest.data.domain.dto.UserRequest;
import penelope.corretagem.penelopeapirest.data.domain.dto.UserResponse;
import penelope.corretagem.penelopeapirest.service.EstateAgentService;
import penelope.corretagem.penelopeapirest.service.UserService;

import java.net.URI;
import java.util.List;

@Tag(name="Usuários")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final EstateAgentService estateAgentService;

    public UserController(UserService userService, EstateAgentService estateAgentService) {
        this.userService = userService;
        this.estateAgentService = estateAgentService;
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

    // Atualiza os dados de um usuário específico pelo ID.
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id,
                                                   @Valid @RequestBody UserRequest userRequestUpdate) {
        UserResponse response = userService.updateUser(id, userRequestUpdate);
        return ResponseEntity.ok(response);
    }
}
