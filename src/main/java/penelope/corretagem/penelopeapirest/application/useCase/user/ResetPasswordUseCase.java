package penelope.corretagem.penelopeapirest.application.useCase.user;

import penelope.corretagem.penelopeapirest.application.dto.ResetPasswordRequest;
import penelope.corretagem.penelopeapirest.core.gateway.IAuthGateway;

public class ResetPasswordUseCase {

    private final IAuthGateway authGateway;

    public ResetPasswordUseCase(IAuthGateway authGateway) {
        this.authGateway = authGateway;
    }

    public void execute(String token, String newPassword) {
        authGateway.resetPassword(new ResetPasswordRequest(token, newPassword));
    }
}