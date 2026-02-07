package penelope.corretagem.penelopeapirest.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
// Serviço responsável pela geração de tokens JWT para autenticação.
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    // Gera um token JWT contendo informações do usuário autenticado.
    public String generateToken(UserDetails userDetails) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            String token = JWT.create()
                    .withIssuer("Penelope-API") // Nome do emissor do token
                    .withSubject(userDetails.getUsername()) // O "dono" do token (neste caso, o e-mail)
                    .withExpiresAt(generateExpirationDate()) // Define a data de expiração
                    .sign(algorithm); // Assina o token
            return token;
        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar o token JWT", exception);
        }
    }

    // Define a data de expiração do token (2 horas a partir do momento atual).
    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public String getEmailFromToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.require(algorithm)
                .withIssuer("Penelope-API")
                .build()
                .verify(token)
                .getSubject();
    }
}
