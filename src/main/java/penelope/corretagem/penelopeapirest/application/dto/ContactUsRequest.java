package penelope.corretagem.penelopeapirest.application.dto;

public record ContactUsRequest(
        String nome,
        String email,
        String assunto,
        String mensagem
) {
}
