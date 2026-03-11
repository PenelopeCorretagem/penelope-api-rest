package penelope.corretagem.penelopeapirest.core.gateway;

import penelope.corretagem.penelopeapirest.oldArchiteture.domain.dto.ContactUsRequest;

public interface IEmailGateway {
    void sendPasswordResetEmail(String toEmail, String token);
    void contactUsEmail(ContactUsRequest contactUsRequest);
}