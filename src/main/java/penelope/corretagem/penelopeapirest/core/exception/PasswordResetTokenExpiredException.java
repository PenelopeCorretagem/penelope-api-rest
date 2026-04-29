package penelope.corretagem.penelopeapirest.core.exception;

public class PasswordResetTokenExpiredException extends RuntimeException {

    public PasswordResetTokenExpiredException() {
        super("Token expirado ou inválido. Por favor, solicite uma nova redefinição de senha.");
    }
}