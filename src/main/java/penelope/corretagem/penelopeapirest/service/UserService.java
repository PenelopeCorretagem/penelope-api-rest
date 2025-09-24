package penelope.corretagem.penelopeapirest.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import penelope.corretagem.penelopeapirest.dto.UserRequest;
import penelope.corretagem.penelopeapirest.dto.UserResponse;
import penelope.corretagem.penelopeapirest.entity.UserEntity;
import penelope.corretagem.penelopeapirest.exception.EmailAlreadyExistsException;
import penelope.corretagem.penelopeapirest.mapper.UserMapper;
import penelope.corretagem.penelopeapirest.repository.UserRepository;

import java.util.List;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    // Adiciona um novo usuário após verificar se o e-mail já está cadastrado.
    public UserResponse addUser(UserRequest userRequest) {
        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("O e-mail informado já está cadastrado");
        }

        UserEntity userEntity = userMapper.toUserEntity(userRequest);

        String encodedPassword = passwordEncoder.encode(userRequest.getSenha());
        userEntity.setSenha(encodedPassword);

        UserEntity savedUser = userRepository.save(userEntity);
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

    // Aplica as atualizações recebidas ao objeto UserEntity.
    private void applyUserUpdates(UserEntity user, UserRequest updateRequest) {
        if (updateRequest.getNomeCompleto() != null) {
            user.setNomeCompleto(updateRequest.getNomeCompleto());
        }
        if (updateRequest.getCpf() != null) {
            user.setCpf(updateRequest.getCpf());
        }
        if (updateRequest.getEmail() != null) {
            user.setEmail(updateRequest.getEmail());
        }
        if (updateRequest.getDtNascimento() != null) {
            user.setDtNascimento(updateRequest.getDtNascimento());
        }
        if (updateRequest.getRendaMensal() != null) {
            user.setRendaMensal(updateRequest.getRendaMensal());
        }
    }
}
