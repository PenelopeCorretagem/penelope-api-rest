package penelope.corretagem.penelopeapirest.application.useCase.user;

import penelope.corretagem.penelopeapirest.core.gateway.IPasswordEncoderGateway;
import penelope.corretagem.penelopeapirest.core.exception.DomainValidationException;
import penelope.corretagem.penelopeapirest.core.user.User;
import penelope.corretagem.penelopeapirest.core.user.repository.IUserRepository;
import penelope.corretagem.penelopeapirest.core.user.valueObject.AccessLevel;
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

        if (request == null) {
            throw new DomainValidationException("Dados do usuario sao obrigatorios");
        }

        if (request.email() == null || request.email().isBlank()) {
            throw new DomainValidationException("O e-mail e obrigatorio");
        }

        if (request.accessLevel() == null) {
            throw new DomainValidationException("O nivel de acesso e obrigatorio");
        }

        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new DomainValidationException("O e-mail informado já está cadastrado");
        }

        if (request.cpf() != null && !request.cpf().isBlank() && userRepository.findByCpf(request.cpf()).isPresent()) {
            throw new DomainValidationException("O CPF informado já está cadastrado");
        }

        if (request.creci() != null && !request.creci().isBlank() && userRepository.findByCreci(request.creci()).isPresent()) {
            throw new DomainValidationException("O CRECI informado já está cadastrado");
        }

        if (request.creci() != null && request.accessLevel() == AccessLevel.CLIENTE) {
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