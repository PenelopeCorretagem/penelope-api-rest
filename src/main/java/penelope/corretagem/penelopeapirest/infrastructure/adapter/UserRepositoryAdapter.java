package penelope.corretagem.penelopeapirest.infrastructure.adapter;

import org.springframework.stereotype.Component;
import penelope.corretagem.penelopeapirest.core.exception.ResourceNotFoundException;
import penelope.corretagem.penelopeapirest.core.user.User;
import penelope.corretagem.penelopeapirest.core.user.repository.IUserRepository;
import penelope.corretagem.penelopeapirest.infrastructure.mapper.UserInfrastructureMapper;
import penelope.corretagem.penelopeapirest.infrastructure.repository.IUserJpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserRepositoryAdapter implements IUserRepository {

    private final IUserJpaRepository jpaRepository;
    private final UserInfrastructureMapper mapper;

    public UserRepositoryAdapter(
            IUserJpaRepository jpaRepository,
            UserInfrastructureMapper mapper
    ) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public List<User> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByCpf(String cpf) {
        return jpaRepository.findByCpf(cpf).map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByCreci(String creci) {
        return jpaRepository.findByCreci(creci).map(mapper::toDomain);
    }

    @Override
    public User save(User user) {
        var entity = mapper.toEntity(user);
        var savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public User update(User user) {
        var existingEntity = jpaRepository.findById(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        existingEntity.setName(user.getName());
        existingEntity.setEmail(user.getEmail());
        existingEntity.setPassword(user.getPassword());
        existingEntity.setCpf(user.getCpf());
        existingEntity.setDateBirth(user.getDateBirth());
        existingEntity.setMonthlyIncome(user.getMonthlyIncome());
        existingEntity.setPhone(user.getPhone());
        existingEntity.setCreci(user.getCreci());
        existingEntity.setAccessLevel(user.getAccessLevel());
        existingEntity.setPasswordResetToken(user.getPasswordResetToken());
        existingEntity.setPasswordResetTokenExpiry(user.getPasswordResetTokenExpiry());

        var savedEntity = jpaRepository.save(existingEntity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public Optional<User> findByPasswordResetToken(String token) {
        return jpaRepository.findByPasswordResetToken(token)
                .map(mapper::toDomain);
    }
}