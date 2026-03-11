package penelope.corretagem.penelopeapirest.application.useCase.user;

import org.springframework.stereotype.Service;
import penelope.corretagem.penelopeapirest.application.dto.UserAuthInfoResponse;
import penelope.corretagem.penelopeapirest.core.gateway.ITokenGateway;
import penelope.corretagem.penelopeapirest.core.user.repository.IUserRepository;
import penelope.corretagem.penelopeapirest.oldArchiteture.service.exception.UserNotFoundException;

@Service
public class GetUserAuthInfoUseCase {

    private final IUserRepository userRepository;
    private final ITokenGateway tokenGateway;

    public GetUserAuthInfoUseCase(IUserRepository userRepository, ITokenGateway tokenGateway) {
        this.userRepository = userRepository;
        this.tokenGateway = tokenGateway;
    }

    public UserAuthInfoResponse execute(String token) {
        String email = tokenGateway.getEmailFromToken(token);

        var user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        return new UserAuthInfoResponse(
                user.getId(),
                user.getAccessLevel().name()
        );
    }
}