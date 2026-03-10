package penelope.corretagem.penelopeapirest.core.gateway;

public interface ITokenGateway {
    String getEmailFromToken(String token);

    // Podemos prever o método de gerar token aqui para usar depois no login
    // String generateToken(Object userDetails);
}