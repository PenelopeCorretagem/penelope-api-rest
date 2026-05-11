package penelope.corretagem.penelopeapirest.core.gateway;

public interface ITokenGateway {
    TokenValidationResult validateToken(String token);

    default String getEmailFromToken(String token) {
        return validateToken(token).email();
    }

    default Integer getAccessLevelFromToken(String token) {
        return validateToken(token).accessLevel();
    }
}