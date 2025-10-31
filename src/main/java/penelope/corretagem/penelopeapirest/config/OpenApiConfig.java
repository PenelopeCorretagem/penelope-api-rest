package penelope.corretagem.penelopeapirest.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;

import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API de Usuários - Penelope",
                version = "1.0.0",
                description = "API REST para gerenciamento de usuários do sistema Penelope.",
                contact = @Contact(
                        name = "Penelope",
                        email = "penelope@email.com",
                        url = "https://penelopecharmosa.com"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "http://springdoc.org"
                )
        ),
        servers = {
                @Server(url = "/", description = "Servidor local")
        },
        tags = {
                @Tag(name = "Usuários", description = "Gerencia usuários do sistema")
        }
)
public class OpenApiConfig {

    /**
     * Registra os schemas ErrorResponse e UserResponse em components/schemas
     */
    @Bean
    public OpenAPI customOpenAPI() {
        Schema<?> errorSchema = new Schema<>()
                .name("ErrorResponse")
                .type("object")
                .addProperty("status", new Schema<>().type("integer").example(400))
                .addProperty("message", new Schema<>().type("string").example("Dados inválidos"))
                .addProperty("details", new Schema<>().type("array")
                        .items(new Schema<>().type("string")).example(List.of("Campo email é obrigatório")))
                .addProperty("timestamp", new Schema<>().type("string").format("date-time")
                        .example("2025-10-05T18:30:00Z"));

        Schema<?> userResponseSchema = new Schema<>()
                .name("UserResponse")
                .type("object")
                .addProperty("nome", new Schema<>().type("string").example("Neto Remelli"))
                .addProperty("cpf", new Schema<>().type("string").example("123.456.789-00"))
                .addProperty("email", new Schema<>().type("string").example("neto@email.com"))
                .addProperty("dtNascimento", new Schema<>().type("string").format("date").example("1990-01-01"))
                .addProperty("rendaMensal", new Schema<>().type("number").format("double").example(5000.0));

        return new OpenAPI()
                .components(new Components()
                        .addSchemas("ErrorResponse", errorSchema)
                        .addSchemas("UserResponse", userResponseSchema)
                );
    }

    /**
     * Adiciona automaticamente respostas de sucesso e erro a todos os endpoints do UserController
     */
    @Bean
    public OperationCustomizer userControllerResponses() {
        return (operation, handlerMethod) -> {
            if (handlerMethod.getBeanType().getSimpleName().equals("UserController")) {

                String method = operation.getOperationId().toLowerCase();

                // Sucesso
                if (method.startsWith("adduser")) { // POST → 201
                    operation.getResponses().addApiResponse("201", new ApiResponse()
                            .description("Usuário criado com sucesso")
                            .content(new Content().addMediaType("application/json",
                                    new MediaType().schema(new Schema<>().$ref("#/components/schemas/UserResponse"))
                            ))
                    );
                } else if (method.startsWith("showallusers") || method.startsWith("updateuser")) { // GET/PATCH → 200
                    operation.getResponses().addApiResponse("200", new ApiResponse()
                            .description("Operação realizada com sucesso")
                            .content(new Content().addMediaType("application/json",
                                    new MediaType().schema(new Schema<>().$ref("#/components/schemas/UserResponse"))
                            ))
                    );
                } else if (method.startsWith("deleteuser")) { // DELETE → 204
                    operation.getResponses().addApiResponse("204", new ApiResponse()
                            .description("Usuário removido com sucesso")
                    );
                }

                // Erros 400/404/500 para todos
                operation.getResponses().addApiResponse("400", new ApiResponse()
                        .description("Dados inválidos")
                        .content(new Content().addMediaType("application/json",
                                new MediaType().schema(new Schema<>().$ref("#/components/schemas/ErrorResponse"))
                        ))
                );

                operation.getResponses().addApiResponse("404", new ApiResponse()
                        .description("Usuário não encontrado")
                        .content(new Content().addMediaType("application/json",
                                new MediaType().schema(new Schema<>().$ref("#/components/schemas/ErrorResponse"))
                        ))
                );

                operation.getResponses().addApiResponse("500", new ApiResponse()
                        .description("Erro interno do servidor")
                        .content(new Content().addMediaType("application/json",
                                new MediaType().schema(new Schema<>().$ref("#/components/schemas/ErrorResponse"))
                        ))
                );
            }
            return operation;
        };
    }
}
