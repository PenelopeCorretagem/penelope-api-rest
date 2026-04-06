package penelope.corretagem.penelopeapirest.core.exception;

public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException() {
        super("Usuário ou senha inválidos");
    }
}
