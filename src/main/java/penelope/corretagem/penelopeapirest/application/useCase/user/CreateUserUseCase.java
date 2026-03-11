package penelope.corretagem.penelopeapirest.application.useCase.user;

import org.springframework.stereotype.Service;
import penelope.corretagem.penelopeapirest.core.gateway.IPasswordEncoderGateway;
import penelope.corretagem.penelopeapirest.core.user.User;
import penelope.corretagem.penelopeapirest.core.user.repository.IUserRepository;
import penelope.corretagem.penelopeapirest.application.dto.UserRequest;
import penelope.corretagem.penelopeapirest.application.dto.UserResponse;
import penelope.corretagem.penelopeapirest.oldArchiteture.service.exception.ClientMustNotPossessACreci;
import penelope.corretagem.penelopeapirest.oldArchiteture.service.exception.UserEmailAlreadyExistsException;

@Service
public class CreateUserUseCase {

    private final IUserRepository userRepository;
    private final IPasswordEncoderGateway passwordEncoder;

    public CreateUserUseCase(IUserRepository userRepository, IPasswordEncoderGateway passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse execute(UserRequest request) {

        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new UserEmailAlreadyExistsException("O e-mail informado já está cadastrado");
        }

        if (request.creci() != null && request.accessLevel().toString().equalsIgnoreCase("CLIENTE")) {
            throw new ClientMustNotPossessACreci("Clientes não devem possuir Creci");
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
                savedUser.getId(), savedUser.getName(), savedUser.getEmail(), savedUser.getPassword(),
                savedUser.getCpf(), savedUser.getDateBirth(), savedUser.getMonthlyIncome(),
                savedUser.getPhone(), savedUser.getCreci(), savedUser.getAccessLevel() ,
                savedUser.getDateCreation(), savedUser.isActive()
        );
    }
}