package penelope.corretagem.penelopeapirest.infrastructure.adapter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import penelope.corretagem.penelopeapirest.core.exception.InvalidCredentialsException;
import penelope.corretagem.penelopeapirest.core.gateway.ITokenGateway;

@Component
public class TokenGatewayAdapter implements ITokenGateway {

    @Value("${app.security.token.secret}")
    private String secret;

    @Override
    public String getEmailFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer("Penelope-API")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException | IllegalArgumentException exception) {
            throw new InvalidCredentialsException();
        }
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
}