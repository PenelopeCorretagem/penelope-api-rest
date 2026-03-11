package penelope.corretagem.penelopeapirest.application.useCase.user;

import org.springframework.stereotype.Service;
import penelope.corretagem.penelopeapirest.core.gateway.IPasswordEncoderGateway;
import penelope.corretagem.penelopeapirest.core.user.repository.IUserRepository;
import penelope.corretagem.penelopeapirest.application.dto.UserResponse;
import penelope.corretagem.penelopeapirest.application.dto.UserUpdateRequest;
import penelope.corretagem.penelopeapirest.oldArchiteture.service.exception.UserNotFoundException;

@Service
public class UpdateUserUseCase {

    private final IUserRepository userRepository;
    private final IPasswordEncoderGateway passwordEncoder;

    public UpdateUserUseCase(IUserRepository userRepository, IPasswordEncoderGateway passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse execute(Long id, UserUpdateRequest req) {

        var user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        user.updateInformation(
                req.name(), req.email(), req.cpf(),
                req.dateBirth(), req.monthlyIncome(), req.phone()
        );

        if (req.password() != null && !req.password().isBlank()) {
            user.changePassword(passwordEncoder.encode(req.password()));
        }

        var updatedUser = userRepository.update(user);

        return new UserResponse(
                updatedUser.getId(), updatedUser.getName(), updatedUser.getEmail(), updatedUser.getPassword(),
                updatedUser.getCpf(), updatedUser.getDateBirth(), updatedUser.getMonthlyIncome(),
                updatedUser.getPhone(), updatedUser.getCreci(), updatedUser.getAccessLevel(),
                updatedUser.getDateCreation(), updatedUser.isActive()
        );
    }
}