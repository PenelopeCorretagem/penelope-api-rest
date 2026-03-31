package penelope.corretagem.penelopeapirest.core.email;

public record ContactMessage(
        String nome,
        String email,
        String assunto,
        String mensagem
) {
}
