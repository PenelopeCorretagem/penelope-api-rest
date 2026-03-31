package penelope.corretagem.penelopeapirest.application.useCase.user;

import penelope.corretagem.penelopeapirest.core.gateway.IPasswordEncoderGateway;
import penelope.corretagem.penelopeapirest.core.exception.DomainValidationException;
import penelope.corretagem.penelopeapirest.core.user.User;
import penelope.corretagem.penelopeapirest.core.user.repository.IUserRepository;
import penelope.corretagem.penelopeapirest.application.dto.UserRequest;
import penelope.corretagem.penelopeapirest.application.dto.UserResponse;

public class CreateUserUseCase {

    private final IUserRepository userRepository;
    private final IPasswordEncoderGateway passwordEncoder;

    public CreateUserUseCase(IUserRepository userRepository, IPasswordEncoderGateway passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse execute(UserRequest request) {

        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new DomainValidationException("O e-mail informado já está cadastrado");
        }

        if (request.creci() != null && request.accessLevel().toString().equalsIgnoreCase("CLIENTE")) {
            throw new DomainValidationException("Clientes não devem possuir Creci");
        }

        User newUser = User.createNew(
                request.name(),
                request.email(),
                passwordEncoder.encode(request.password()),
                request.cpf(),
                request.dateBirth(),
                request.monthlyIncome(),
                request.phone(),
                request.creci(),
                request.accessLevel()
        );

        User savedUser = userRepository.save(newUser);

        return new UserResponse(
                savedUser.getId(), savedUser.getName(), savedUser.getEmail(),
                savedUser.getCpf(), savedUser.getDateBirth(), savedUser.getMonthlyIncome(),
                savedUser.getPhone(), savedUser.getCreci(), savedUser.getAccessLevel(), savedUser.isActive()
        );
    }
}