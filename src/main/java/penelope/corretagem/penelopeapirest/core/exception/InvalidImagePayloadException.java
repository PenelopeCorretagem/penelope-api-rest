package penelope.corretagem.penelopeapirest.core.exception;

public class InvalidImagePayloadException extends RuntimeException {

    public InvalidImagePayloadException(String fileName, Throwable cause) {
        super("Erro ao ler o arquivo: " + fileName, cause);
    }
}
