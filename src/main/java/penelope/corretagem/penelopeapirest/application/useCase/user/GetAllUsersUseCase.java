package penelope.corretagem.penelopeapirest.application.useCase.user;

import org.springframework.stereotype.Service;
import penelope.corretagem.penelopeapirest.core.user.repository.IUserRepository;
import penelope.corretagem.penelopeapirest.data.domain.dto.UserResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetAllUsersUseCase {

    private final IUserRepository userRepository;

    public GetAllUsersUseCase(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponse> execute() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponse(
                        user.getId(), user.getName(), user.getEmail(),
                        user.getPassword(),
                        user.getCpf(), user.getDateBirth(), user.getMonthlyIncome(),
                        user.getPhone(), user.getCreci(), user.getAccessLevel(),
                        user.getDateCreation(), user.isActive()))
                .collect(Collectors.toList());
    }
}