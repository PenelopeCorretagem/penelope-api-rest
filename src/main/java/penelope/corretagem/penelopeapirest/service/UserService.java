package penelope.corretagem.penelopeapirest.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import penelope.corretagem.penelopeapirest.dto.UserRequest;
import penelope.corretagem.penelopeapirest.dto.UserResponse;
import penelope.corretagem.penelopeapirest.entity.UserEntity;
import penelope.corretagem.penelopeapirest.mapper.UserMapper;
import penelope.corretagem.penelopeapirest.repository.UserRepository;

import java.util.List;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserResponse addUser(UserRequest user) {
        UserEntity userEntity = userMapper.toUserEntity(user);
        UserEntity savedUser = userRepository.save(userEntity);
        log.info("Usuário adicionado com sucesso: {}", savedUser.getId());
        return userMapper.toUserResponse(savedUser);
    }

    public ResponseEntity<List<UserResponse>> showAllUser() {
        List<UserEntity> users = userRepository.findAll();

        if (users.isEmpty()) {
            log.info("Nenhum usuário encontrado.");
            return ResponseEntity.noContent().build();
        }

        List<UserResponse> usersResponse = users.stream()
                .map(userMapper::toUserResponse)
                .toList();

        log.info("Total de usuários retornados: {}", usersResponse.size());
        return ResponseEntity.ok(usersResponse);
    }

    public UserResponse updateUser(Long id, UserRequest userRequestUpdate) {
        UserEntity user = userRepository.findById(id).orElseThrow(
                () -> {
                    log.warn("Usuário com id {} não encontrado para atualização.", id);
                    return new RuntimeException("Usuário não encontrado");
                });

        applyUserUpdates(user, userRequestUpdate);

        UserEntity savedUser = userRepository.save(user);
        log.info("Usuário atualizado com sucesso (parcial): {}", savedUser.getId());
        return userMapper.toUserResponse(savedUser);
    }


    public ResponseEntity<Void> deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            log.warn("Tentativa de deletar usuário com id inexistente: {}", id);
            return ResponseEntity.notFound().build();
        }

        userRepository.deleteById(id);
        log.info("Usuário deletado com sucesso: {}", id);
        return ResponseEntity.noContent().build();
    }

    private void applyUserUpdates(UserEntity user, UserRequest updateRequest) {
        if (updateRequest.getNome() != null) {
            user.setNome(updateRequest.getNome());
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
