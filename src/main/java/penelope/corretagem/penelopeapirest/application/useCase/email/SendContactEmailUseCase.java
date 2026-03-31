package penelope.corretagem.penelopeapirest.application.useCase.email;

import penelope.corretagem.penelopeapirest.core.gateway.IEmailGateway;
import penelope.corretagem.penelopeapirest.oldArchiteture.domain.dto.ContactUsRequest;

public class SendContactEmailUseCase {

    private final IEmailGateway emailGateway;

    public SendContactEmailUseCase(IEmailGateway emailGateway) {
        this.emailGateway = emailGateway;
    }

    public void execute(ContactUsRequest request) {
        emailGateway.contactUsEmail(request);
    }
}
