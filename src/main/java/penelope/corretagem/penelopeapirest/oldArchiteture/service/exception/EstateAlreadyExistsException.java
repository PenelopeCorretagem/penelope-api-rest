package penelope.corretagem.penelopeapirest.oldArchiteture.service.exception;

public class EstateAlreadyExistsException extends RuntimeException {
  public EstateAlreadyExistsException() {
    super("Imóvel já cadastrado.");
  }
  public EstateAlreadyExistsException(String message) {
    super(message);
  }
}
