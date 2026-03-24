package penelope.corretagem.penelopeapirest.core.gateway;

import org.springframework.security.core.userdetails.UserDetails;

public interface ITokenGateway {
    String generateToken(String email, String accessLevel);

    String getEmailFromToken(String token);

    String getAccessLevelFromToken(String token);
}