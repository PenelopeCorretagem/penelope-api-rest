package penelope.corretagem.penelopeapirest.application.useCase.user;

import penelope.corretagem.penelopeapirest.application.dto.UserAuthInfoResponse;
import penelope.corretagem.penelopeapirest.core.exception.ResourceNotFoundException;
import penelope.corretagem.penelopeapirest.core.gateway.ITokenGateway;
import penelope.corretagem.penelopeapirest.core.user.repository.IUserRepository;

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
            .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        return new UserAuthInfoResponse(
                user.getId(),
                user.getAccessLevel().name()
        );
    }
}