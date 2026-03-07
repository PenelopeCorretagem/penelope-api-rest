package penelope.corretagem.penelopeapirest.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import penelope.corretagem.penelopeapirest.data.domain.dto.UserAuthInfo;
import penelope.corretagem.penelopeapirest.data.domain.dto.UserRequest;
import penelope.corretagem.penelopeapirest.data.domain.dto.UserResponse;
import penelope.corretagem.penelopeapirest.data.domain.dto.UserUpdateRequest;
import penelope.corretagem.penelopeapirest.data.domain.entity.UserEntity;
import penelope.corretagem.penelopeapirest.data.domain.repository.UserRepository;
import penelope.corretagem.penelopeapirest.mapper.UserMapper;
import penelope.corretagem.penelopeapirest.service.exception.ClientMustNotPossessACreci;
import penelope.corretagem.penelopeapirest.service.exception.InvalidTokenException;
import penelope.corretagem.penelopeapirest.service.exception.UserEmailAlreadyExistsException;
import penelope.corretagem.penelopeapirest.service.exception.UserNotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final TokenService tokenService;

    public UserService(
            UserRepository userRepository,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder,
            EmailService emailService,
            TokenService tokenService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    // Adiciona um novo usuário após verificar se o e-mail já está cadastrado.
    @Transactional
    public UserResponse addUser(UserRequest userRequest) {
        if (userRepository.findByEmail(userRequest.email()).isPresent()) {
            throw new UserEmailAlreadyExistsException("O e-mail informado já está cadastrado");
        }

        if (userRequest.creci() != null && userRequest.accessLevel().toString().equalsIgnoreCase("CLIENTE")) {
            throw new ClientMustNotPossessACreci("Clientes não devem possuir Creci");
        }

        UserEntity entity = userMapper.toUserEntity(userRequest);
        entity.setDateCreation(LocalDateTime.now());
        entity.setActive(true);

        entity.setPassword(passwordEncoder.encode(userRequest.password()));

        userRepository.save(entity);

        return userMapper.toUserResponse(entity);
    }

    // Retorna todos os usuários cadastrados no sistema.
    public List<UserResponse> showAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    public UserResponse getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toUserResponse)
                .orElseThrow(UserNotFoundException::new);
    }

    public UserResponse getUserpasswordResetToken(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toUserResponse)
                .orElseThrow(UserNotFoundException::new);
    }

    // Atualiza os dados de um usuário existente com base no ID.
    @Transactional
    public UserResponse updateUser(Long id, UserUpdateRequest req) {

        UserEntity user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        if (req.name() != null)
            user.setName(req.name());

        if (req.email() != null)
            user.setEmail(req.email());

        if (req.password() != null)
            user.setPassword(passwordEncoder.encode(req.password()));

        if (req.cpf() != null)
            user.setCpf(req.cpf());

        if (req.dateBirth() != null)
            user.setDateBirth(req.dateBirth());

        if (req.monthlyIncome() != null)
            user.setMonthlyIncome(req.monthlyIncome());

        if (req.phone() != null)
            user.setPhone(req.phone());

        userRepository.save(user);

        return userMapper.toUserResponse(user);
    }


    // Remove um usuário do sistema com base no ID.
    @Transactional
    public ResponseEntity<Void> deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public void generatePasswordResetToken(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            String token = UUID.randomUUID().toString();
            Date expiryDate = new Date(System.currentTimeMillis() + 3600_000);

            user.setPasswordResetToken(token);
            user.setPasswordResetTokenExpiry(expiryDate);

            userRepository.save(user);

            emailService.sendPasswordResetEmail(user.getEmail(), token);
        });
    }

    // Valida o token
    public void validatePasswordResetToken(String token) {
        UserEntity user = userRepository.findByPasswordResetToken(token)
                .orElseThrow(() -> new InvalidTokenException("Token inválido ou não encontrado."));

        // Verifica se o token expirou
        if (user.getPasswordResetTokenExpiry().before(new Date())) {
            throw new InvalidTokenException("Token expirado. Por favor, solicite uma nova redefinição de senha.");
        }
    }


    public void resetPassword(String token, String newPassword) {
        // 1. Revalida o token para garantir que ainda é válido no momento da troca
        validatePasswordResetToken(token);

        UserEntity user = userRepository.findByPasswordResetToken(token).get();

        // 2. Criptografa a nova senha
        user.setPassword(passwordEncoder.encode(newPassword));

        // 3. Limpa o token para que ele não possa ser usado novamente
        user.setPasswordResetToken(null);
        user.setPasswordResetTokenExpiry(null);

        // 4. Salva o usuário com a nova senha
        userRepository.save(user);
    }

    public UserAuthInfo getUserAuthInfoFromToken(String token) {
        String email = tokenService.getEmailFromToken(token);

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        return new UserAuthInfo(
                user.getId(),
                user.getAccessLevel().getDescription()
        );
    }
}
