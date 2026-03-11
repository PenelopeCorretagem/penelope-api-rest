package penelope.corretagem.penelopeapirest.oldArchiteture.service.exception;

public class AcessLevelException extends RuntimeException {
  public AcessLevelException() {
    super("Nível de acesso inválido.");
  }

  public AcessLevelException(String message) {
    super(message);
  }
}
