package penelope.corretagem.penelopeapirest.util;

/**
 * Exceção para erros de validação no corpo da requisição (payload).
 * <p>
 * É lançada quando os dados de entrada (DTO) falham em validações
 * sintáticas ou de formato (ex: campo nulo, formato de e-mail inválido).
 */
public class InvalidPayloadException extends RuntimeException {
  public InvalidPayloadException(String message) {
    super(message);
  }
}
