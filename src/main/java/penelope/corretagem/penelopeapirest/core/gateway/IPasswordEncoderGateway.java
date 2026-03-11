package penelope.corretagem.penelopeapirest.core.gateway;

public interface IPasswordEncoderGateway {
    String encode(String rawPassword);

    boolean matches(String rawPassword, String encodedPassword);
}
