package penelope.corretagem.penelopeapirest.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import penelope.corretagem.penelopeapirest.dto.UserRequest;
import penelope.corretagem.penelopeapirest.dto.UserResponse;
import penelope.corretagem.penelopeapirest.entity.UserEntity;
import penelope.corretagem.penelopeapirest.mapper.UserMapper;
import penelope.corretagem.penelopeapirest.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse addUser(UserRequest user) {
        UserEntity userEntity = UserMapper.toUserEntity(user);
        UserEntity savedUser = userRepository.save(userEntity);
        return UserMapper.toUserResponse(savedUser);
    }

    public ResponseEntity<List<UserResponse>> showAllUser() {
        List<UserEntity> users = userRepository.findAll();

        if (users.isEmpty()) return ResponseEntity.noContent().build();

        List<UserResponse> usersResponse = users.stream()
                .map(UserMapper::toUserResponse)
                .toList();

        return ResponseEntity.ok(usersResponse);
    }

    public UserResponse updateUser(Long id, UserRequest userRequestUpdate) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        user.setNome(userRequestUpdate.getNome());
        user.setCpf(userRequestUpdate.getCpf());
        user.setEmail(userRequestUpdate.getEmail());
        user.setDtNascimento(userRequestUpdate.getDtNascimento());
        user.setRendaMensal(userRequestUpdate.getRendaMensal());

        UserEntity savedUser = userRepository.save(user);

        return UserMapper.toUserResponse(savedUser);
    }

    public ResponseEntity<Void> deleteUser(Long id) {
        if (!userRepository.existsById(id)) return ResponseEntity.notFound().build();

        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
