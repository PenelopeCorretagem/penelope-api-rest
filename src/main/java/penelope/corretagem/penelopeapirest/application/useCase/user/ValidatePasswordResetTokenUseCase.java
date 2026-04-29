package penelope.corretagem.penelopeapirest.application.useCase.user;

import penelope.corretagem.penelopeapirest.application.dto.ValidateTokenRequest;
import penelope.corretagem.penelopeapirest.core.gateway.IAuthGateway;

public class ValidatePasswordResetTokenUseCase {

    private final IAuthGateway authGateway;

    public ValidatePasswordResetTokenUseCase(IAuthGateway authGateway) {
        this.authGateway = authGateway;
    }

    public void execute(String token) {
        authGateway.validateResetToken(new ValidateTokenRequest(token));
    }
}