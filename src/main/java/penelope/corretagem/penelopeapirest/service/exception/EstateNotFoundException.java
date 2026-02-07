package penelope.corretagem.penelopeapirest.service.exception;

public class EstateNotFoundException extends RuntimeException {
  public EstateNotFoundException() {
    super("Empreendimento não encontrado.");
  }
}
