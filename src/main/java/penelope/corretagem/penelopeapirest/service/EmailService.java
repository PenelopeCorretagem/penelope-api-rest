package penelope.corretagem.penelopeapirest.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendPasswordResetEmail(String toEmail, String token) {

        String resetUrl = frontendUrl + "/verificacao?token=" + token;
        String manualUrl = frontendUrl + "/verificacao";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Seu Token de Redefinição de Senha");

        String emailBody = String.format(
                "Olá,\n\n" +
                        "Você solicitou a redefinição de sua senha.\n\n" +
                        "Clique no link abaixo para ir diretamente para a página de verificação:\n" +
                        "%s\n\n" +
                        "Se o link acima não funcionar, você pode acessar manualmente o endereço abaixo e inserir o token:\n" +
                        "%s\n\n" +
                        "Código de verificação: %s\n\n" +
                        "Este link e código irão expirar em 1 hora.\n\n" +
                        "Se você não solicitou isso, por favor, ignore este e-mail.\n\n" +
                        "Atenciosamente,\nEquipe Penelope",
                resetUrl,
                manualUrl,
                token
        );

        message.setText(emailBody);

        mailSender.send(message);
        System.out.println("E-mail de redefinição de senha enviado para: " + toEmail);
        System.out.println("Link de redefinição: " + resetUrl);
        System.out.println("Link alternativo: " + manualUrl);
    }
}