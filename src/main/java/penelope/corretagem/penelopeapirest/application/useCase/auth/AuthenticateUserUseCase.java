package penelope.corretagem.penelopeapirest.application.useCase.auth;


import org.springframework.stereotype.Service;
import penelope.corretagem.penelopeapirest.core.gateway.IPasswordEncoderGateway;
import penelope.corretagem.penelopeapirest.core.gateway.ITokenGateway;
import penelope.corretagem.penelopeapirest.core.user.repository.IUserRepository;
import penelope.corretagem.penelopeapirest.application.dto.LoginRequest;
import penelope.corretagem.penelopeapirest.application.dto.LoginResponse;

@Service
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
                .orElseThrow(() -> new RuntimeException("Usuário ou senha inválidos"));

        if (!passwordEncoderGateway.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("Usuário ou senha inválidos");
        }

        String token = tokenGateway.generateToken(new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), java.util.Collections.emptyList()
        ));

        return new LoginResponse(token, user.getId(), user.getAccessLevel().toString());
    }
}