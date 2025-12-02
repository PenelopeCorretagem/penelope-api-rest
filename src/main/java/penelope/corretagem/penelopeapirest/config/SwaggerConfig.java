package penelope.corretagem.penelopeapirest.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "bearerAuth", // Nome que usaremos para referenciar
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SwaggerConfig {
    // Essa classe pode estar vazia, a anotação faz todo o trabalho.
    // Ela avisa ao Swagger: "Ei, essa API aceita tokens Bearer JWT".
}

