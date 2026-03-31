package penelope.corretagem.penelopeapirest.core.gateway;

import penelope.corretagem.penelopeapirest.core.email.ContactMessage;

public interface IEmailGateway {
    void sendPasswordResetEmail(String toEmail, String token);
    void contactUsEmail(ContactMessage contactMessage);
}