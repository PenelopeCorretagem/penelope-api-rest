package penelope.corretagem.penelopeapirest.service.exception;

public class EstateAgentAlreadyExistsException extends RuntimeException {
  public EstateAgentAlreadyExistsException() {
    super("Corretor já cadastrado.");
  }

  public EstateAgentAlreadyExistsException(String message) {
    super(message);
  }
}
