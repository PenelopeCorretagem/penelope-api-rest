package penelope.corretagem.penelopeapirest.application.useCase.user;

import penelope.corretagem.penelopeapirest.core.exception.ResourceNotFoundException;
import penelope.corretagem.penelopeapirest.core.gateway.IPasswordEncoderGateway;
import penelope.corretagem.penelopeapirest.core.user.repository.IUserRepository;
import penelope.corretagem.penelopeapirest.application.dto.UserResponse;
import penelope.corretagem.penelopeapirest.application.dto.UserUpdateRequest;

public class UpdateUserUseCase {

    private final IUserRepository userRepository;
    private final IPasswordEncoderGateway passwordEncoder;

    public UpdateUserUseCase(IUserRepository userRepository, IPasswordEncoderGateway passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse execute(Long id, UserUpdateRequest req) {

        var user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        user.updateInformation(
                req.name(), req.email(), req.cpf(),
                req.dateBirth(), req.monthlyIncome(), req.phone()
        );

        if (req.password() != null && !req.password().isBlank()) {
            user.changePassword(passwordEncoder.encode(req.password()));
        }

        var updatedUser = userRepository.update(user);

        return new UserResponse(
                updatedUser.getId(), updatedUser.getName(), updatedUser.getEmail(),
                updatedUser.getCpf(), updatedUser.getDateBirth(), updatedUser.getMonthlyIncome(),
                updatedUser.getPhone(), updatedUser.getCreci(), updatedUser.getAccessLevel(), updatedUser.isActive()
        );
    }
}