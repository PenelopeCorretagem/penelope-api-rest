package penelope.corretagem.penelopeapirest.application.useCase.auth;


import penelope.corretagem.penelopeapirest.core.gateway.IPasswordEncoderGateway;
import penelope.corretagem.penelopeapirest.core.gateway.ITokenGateway;
import penelope.corretagem.penelopeapirest.core.exception.InvalidCredentialsException;
import penelope.corretagem.penelopeapirest.core.user.repository.IUserRepository;
import penelope.corretagem.penelopeapirest.application.dto.LoginRequest;
import penelope.corretagem.penelopeapirest.application.dto.LoginResponse;

public class AuthenticateUserUseCase {

    private final IUserRepository userRepository;
    private final IPasswordEncoderGateway passwordEncoderGateway;
    private final ITokenGateway tokenGateway;

    public AuthenticateUserUseCase(IUserRepository userRepository,
                                   IPasswordEncoderGateway passwordEncoderGateway,
                                   ITokenGateway tokenGateway) {
        this.userRepository = userRepository;
        this.passwordEncoderGateway = passwordEncoderGateway;
        this.tokenGateway = tokenGateway;
    }

    public LoginResponse execute(LoginRequest request) {

        var user = userRepository.findByEmail(request.email())
                .orElseThrow(InvalidCredentialsException::new);

        String role = user.getAccessLevel().name();

        if (!passwordEncoderGateway.matches(request.password(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        String token = tokenGateway.generateToken(user.getEmail(), role);

        return new LoginResponse(token, user.getId(), user.getAccessLevel().toString());
    }
}