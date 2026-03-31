package penelope.corretagem.penelopeapirest.infrastructure.adapter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import penelope.corretagem.penelopeapirest.core.exception.IntegrationException;
import penelope.corretagem.penelopeapirest.core.gateway.ITokenGateway;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class TokenGatewayAdapter implements ITokenGateway {

    @Value("${app.security.token.secret}")
    private String secret;

    @Override
    public String generateToken(String email, String accessLevel) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer("Penelope-API")
                    .withSubject(email) // Passa a string do e-mail diretamente
                    .withClaim("accessLevel", accessLevel) // Usa o parâmetro accessLevel
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new IntegrationException("Erro ao gerar o token JWT", exception);
        }
    }

    @Override
    public String getEmailFromToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.require(algorithm)
                .withIssuer("Penelope-API")
                .build()
                .verify(token)
                .getSubject();
    }

    @Override
    public String getAccessLevelFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("Penelope-API")
                    .build()
                    .verify(token)
                    .getClaim("accessLevel")
                    .asString();
        } catch (Exception exception) {
            return "";
        }
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}