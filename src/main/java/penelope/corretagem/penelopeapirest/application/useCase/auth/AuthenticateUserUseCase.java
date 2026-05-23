package penelope.corretagem.penelopeapirest.application.useCase.auth;


import penelope.corretagem.penelopeapirest.application.dto.LoginRequest;
import penelope.corretagem.penelopeapirest.application.dto.LoginResponse;
import penelope.corretagem.penelopeapirest.core.gateway.IAuthGateway;

public class AuthenticateUserUseCase {

    private final IAuthGateway authGateway;

    public AuthenticateUserUseCase(IAuthGateway authGateway) {
        this.authGateway = authGateway;
    }

    public LoginResponse execute(LoginRequest request) {
       return authGateway.authenticate(request);
    }
}