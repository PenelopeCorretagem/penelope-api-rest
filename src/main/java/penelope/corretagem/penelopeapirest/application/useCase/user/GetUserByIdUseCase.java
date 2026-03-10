package penelope.corretagem.penelopeapirest.application.useCase.user;

import org.springframework.stereotype.Service;
import penelope.corretagem.penelopeapirest.core.user.repository.IUserRepository;
import penelope.corretagem.penelopeapirest.data.domain.dto.UserResponse;
import penelope.corretagem.penelopeapirest.service.exception.UserNotFoundException;

@Service
public class GetUserByIdUseCase {

    private final IUserRepository userRepository;

    public GetUserByIdUseCase(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse execute(Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        return new UserResponse(
                user.getId(), user.getName(), user.getEmail(), user.getPassword(),
                user.getCpf(), user.getDateBirth(), user.getMonthlyIncome(),
                user.getPhone(), user.getCreci(), user.getAccessLevel(),
                user.getDateCreation(), user.isActive()
        );
    }
}