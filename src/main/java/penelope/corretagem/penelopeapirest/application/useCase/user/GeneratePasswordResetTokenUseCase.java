package penelope.corretagem.penelopeapirest.application.useCase.user;

import penelope.corretagem.penelopeapirest.application.dto.ForgotPasswordRequest;
import penelope.corretagem.penelopeapirest.core.gateway.IAuthGateway;

public class GeneratePasswordResetTokenUseCase {

    private final IAuthGateway authGateway;

    public GeneratePasswordResetTokenUseCase(IAuthGateway authGateway) {
        this.authGateway = authGateway;
    }

    public void execute(String email) {
        authGateway.forgotPassword(new ForgotPasswordRequest(email));
    }
}