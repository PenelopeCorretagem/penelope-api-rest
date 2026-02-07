package penelope.corretagem.penelopeapirest.service.exception;

public class ClientAlreadyExistsException extends RuntimeException {
  public ClientAlreadyExistsException() {
    super("Cliente já cadastrado.");
  }
  public ClientAlreadyExistsException(String message) {
    super(message);
  }
}
