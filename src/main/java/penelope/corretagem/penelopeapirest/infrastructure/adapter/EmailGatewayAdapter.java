package penelope.corretagem.penelopeapirest.infrastructure.adapter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import penelope.corretagem.penelopeapirest.core.email.ContactMessage;
import penelope.corretagem.penelopeapirest.core.gateway.IEmailGateway;

@Component
public class EmailGatewayAdapter implements IEmailGateway {

    private final JavaMailSender mailSender;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    public EmailGatewayAdapter(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendPasswordResetEmail(String toEmail, String token) {

        String resetUrl = frontendUrl + "verificacao?token=" + token;
        String manualUrl = frontendUrl + "verificacao";

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

    public void contactUsEmail(ContactMessage contactUsRequest) {

        String nome = contactUsRequest.nome();
        String email = contactUsRequest.email();
        String assunto = contactUsRequest.assunto();
        String mensagem = contactUsRequest.mensagem();

        SimpleMailMessage message = new SimpleMailMessage();

        String emailBody = String.format(
                "Olá, alguém te enviou um email \n\n" +
                        "Email: %s.\n\n" +
                        "mensagem: %s.\n\n" +
                        "Atenciosamente,\n" +
                        "%s",
                email,
                mensagem,
                nome
        );

        message.setTo("penelopedevelop@gmail.com");
        message.setCc(email);
        message.setSubject(assunto);
        message.setText(emailBody);

        mailSender.send(message);
    }
}