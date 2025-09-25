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

        String resetUrl = frontendUrl + "/verificar-codigo?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Seu Token de Redefinição de Senha");

        String emailBody = String.format(
                "Olá,\n\nVocê solicitou a redefinição de sua senha.\n\n" +
                        "Por favor, clique no link abaixo para ir para a página de verificação:\n" +
                        "%s\n\n" +
                        "Se o link não funcionar, você pode usar o seguinte código de verificação na página:\n" +
                        "Código: %s\n\n" +
                        "Este link e código irão expirar em 1 hora.\n\n" +
                        "Se você não solicitou isso, por favor, ignore este e-mail.\n\n" +
                        "Atenciosamente,\nEquipe Penelope",
                resetUrl,
                token
        );

        message.setText(emailBody);

        mailSender.send(message);
        System.out.println("E-mail de redefinição de senha enviado para: " + toEmail);
        System.out.println("Link de redefinição: " + resetUrl);
    }
}