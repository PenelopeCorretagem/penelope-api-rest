package penelope.corretagem.penelopeapirest.core.gateway;

public interface ITokenGateway {
    String getEmailFromToken(String token);

    String getAccessLevelFromToken(String token);
}