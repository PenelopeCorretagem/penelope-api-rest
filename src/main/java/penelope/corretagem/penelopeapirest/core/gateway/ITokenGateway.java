package penelope.corretagem.penelopeapirest.core.gateway;

import org.springframework.security.core.userdetails.UserDetails;

public interface ITokenGateway {
    String getEmailFromToken(String token);

    String generateToken(UserDetails userDetails);
}