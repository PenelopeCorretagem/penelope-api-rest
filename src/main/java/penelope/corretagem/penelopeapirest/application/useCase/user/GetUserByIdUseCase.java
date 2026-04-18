package penelope.corretagem.penelopeapirest.application.useCase.user;

import penelope.corretagem.penelopeapirest.core.exception.ResourceNotFoundException;
import penelope.corretagem.penelopeapirest.core.user.repository.IUserRepository;
import penelope.corretagem.penelopeapirest.application.dto.UserResponse;

public class    GetUserByIdUseCase {

    private final IUserRepository userRepository;

    public GetUserByIdUseCase(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse execute(Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        return new UserResponse(
                user.getId(), user.getName(), user.getEmail(),
                user.getCpf(), user.getDateBirth(), user.getMonthlyIncome(),
                user.getPhone(), user.getCreci(), user.getAccessLevel(), user.isActive()
        );
    }
}