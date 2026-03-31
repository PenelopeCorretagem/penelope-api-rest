package penelope.corretagem.penelopeapirest.application.useCase.email;

import penelope.corretagem.penelopeapirest.application.dto.ContactUsRequest;
import penelope.corretagem.penelopeapirest.core.email.ContactMessage;
import penelope.corretagem.penelopeapirest.core.gateway.IEmailGateway;

public class SendContactEmailUseCase {

    private final IEmailGateway emailGateway;

    public SendContactEmailUseCase(IEmailGateway emailGateway) {
        this.emailGateway = emailGateway;
    }

    public void execute(ContactUsRequest request) {
        var contactMessage = new ContactMessage(
                request.nome(),
                request.email(),
                request.assunto(),
                request.mensagem()
        );

        emailGateway.contactUsEmail(contactMessage);
    }
}
