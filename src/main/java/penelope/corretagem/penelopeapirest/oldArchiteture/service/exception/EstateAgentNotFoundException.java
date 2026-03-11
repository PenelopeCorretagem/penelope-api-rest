package penelope.corretagem.penelopeapirest.oldArchiteture.service.exception;

public class EstateAgentNotFoundException extends RuntimeException {
  public EstateAgentNotFoundException() {
    super("Corretor não encontrado.");
  }
  public EstateAgentNotFoundException(String message) {
    super(message);
  }

  public EstateAgentNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

}
