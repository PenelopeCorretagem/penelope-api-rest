package penelope.corretagem.penelopeapirest.oldArchiteture.service.exception;

public class EstateNotFoundException extends RuntimeException {
  public EstateNotFoundException() {
    super("Empreendimento não encontrado.");
  }
}
