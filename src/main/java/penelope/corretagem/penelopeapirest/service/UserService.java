package penelope.corretagem.penelopeapirest.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import penelope.corretagem.penelopeapirest.dto.UserRequest;
import penelope.corretagem.penelopeapirest.dto.UserResponse;
import penelope.corretagem.penelopeapirest.entity.ClientEntity;
import penelope.corretagem.penelopeapirest.entity.UserEntity;
import penelope.corretagem.penelopeapirest.event.UserRegisteredEvent;
import penelope.corretagem.penelopeapirest.exception.EmailAlreadyExistsException;
import penelope.corretagem.penelopeapirest.exception.InvalidTokenException;
import penelope.corretagem.penelopeapirest.mapper.UserMapper;
import penelope.corretagem.penelopeapirest.repository.ClientRepository;
import penelope.corretagem.penelopeapirest.repository.UserRepository;

import java.time.LocalDate;
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
    private final ApplicationEventPublisher eventPublisher;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, EmailService emailService, ApplicationEventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.eventPublisher = eventPublisher;
    }


    // Adiciona um novo usuário após verificar se o e-mail já está cadastrado.
    @Transactional
    public UserResponse addUser(UserRequest userRequest) {
        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("O e-mail informado já está cadastrado");
        }

        // 4. Cria e prepara a entidade de autenticação
        UserEntity newUser = new UserEntity();
        newUser.setNomeCompleto(userRequest.getNomeCompleto());
        newUser.setEmail(userRequest.getEmail());
        newUser.setSenha(passwordEncoder.encode(userRequest.getSenha()));
        newUser.setNivelAcesso(UserEntity.NivelAcesso.Cliente);
        newUser.setAtivo(true);
        newUser.setDtCriacao(LocalDate.now());

        UserEntity savedUser = userRepository.save(newUser);

        eventPublisher.publishEvent(new UserRegisteredEvent(this, savedUser));

        return userMapper.toUserResponse(savedUser);
    }

    // Retorna todos os usuários cadastrados no sistema.
    public List<UserResponse> showAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    // Atualiza os dados de um usuário existente com base no ID.
    public UserResponse updateUser(Long id, UserRequest userRequestUpdate) {
        UserEntity user = userRepository.findById(id).orElseThrow(
                () -> {
                    return new RuntimeException("Usuário não encontrado");
                });

        applyUserUpdates(user, userRequestUpdate);

        UserEntity savedUser = userRepository.save(user);
        return userMapper.toUserResponse(savedUser);
    }

    // Remove um usuário do sistema com base no ID.
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
        user.setSenha(passwordEncoder.encode(newPassword));

        // 3. Limpa o token para que ele não possa ser usado novamente
        user.setPasswordResetToken(null);
        user.setPasswordResetTokenExpiry(null);

        // 4. Salva o usuário com a nova senha
        userRepository.save(user);
    }

    // Aplica as atualizações recebidas ao objeto UserEntity.
    private void applyUserUpdates(UserEntity user, UserRequest updateRequest) {
        if (updateRequest.getNomeCompleto() != null) {
            user.setNomeCompleto(updateRequest.getNomeCompleto());
        }
        if (updateRequest.getEmail() != null) {
            user.setEmail(updateRequest.getEmail());
        }
        if (updateRequest.getDtNascimento() != null) {
            user.setDtNascimento(updateRequest.getDtNascimento());
        }
    }
}
