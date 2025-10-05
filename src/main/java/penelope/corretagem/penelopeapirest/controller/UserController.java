package penelope.corretagem.penelopeapirest.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import penelope.corretagem.penelopeapirest.dto.UserRequest;
import penelope.corretagem.penelopeapirest.dto.UserResponse;
import penelope.corretagem.penelopeapirest.service.UserService;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;

@Tag(
        name = "Usuários",
        description = "Endpoints responsáveis pelo gerenciamento de usuários do sistema."
)
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Cadastra um novo usuário",
            description = "Cria um novo usuário com as informações fornecidas no corpo da requisição.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do novo usuário a ser cadastrado",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UserRequest.class))
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{\"nome\":\"O campo nome é obrigatório\"}"))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{\"erro\":\"Ocorreu um erro inesperado\"}")))
    })
    @PostMapping
    public ResponseEntity<UserResponse> addUser(
            @Valid @RequestBody UserRequest userRequest
    ) {
        UserResponse response = userService.addUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Lista todos os usuários",
            description = "Retorna uma lista contendo todos os usuários cadastrados no sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)))),
            @ApiResponse(responseCode = "204", description = "Nenhum usuário encontrado")
    })
    @GetMapping
    public ResponseEntity<List<UserResponse>> showAllUsers() {
        return userService.showAllUser();
    }

    @Operation(
            summary = "Atualiza um usuário existente",
            description = "Atualiza parcialmente os dados de um usuário com base no ID informado.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Novos dados do usuário",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UserRequest.class))
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{\"nome\":\"O campo nome é obrigatório\"}"))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{\"erro\":\"Usuário com id 1 não encontrado\"}"))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{\"erro\":\"Ocorreu um erro inesperado\"}")))
    })
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @Parameter(description = "ID do usuário a ser atualizado", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody UserRequest userRequestUpdate
    ) {
        UserResponse response = userService.updateUser(id, userRequestUpdate);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Remove um usuário do sistema",
            description = "Deleta o usuário identificado pelo ID informado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{\"erro\":\"Usuário com id 1 não encontrado\"}"))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{\"erro\":\"Ocorreu um erro inesperado\"}")))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID do usuário a ser removido", example = "1")
            @PathVariable Long id
    ) {
        return userService.deleteUser(id);
    }
}
