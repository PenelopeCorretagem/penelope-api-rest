package penelope.corretagem.penelopeapirest.infrastructure.adapter;

import org.springframework.stereotype.Component;
import penelope.corretagem.penelopeapirest.core.user.User;
import penelope.corretagem.penelopeapirest.core.user.repository.IUserRepository;
import penelope.corretagem.penelopeapirest.infrastructure.mapper.UserInfrastructureMapper;
import penelope.corretagem.penelopeapirest.infrastructure.repository.IUserJpaRepository;

import java.util.Optional;

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
    public Optional<User> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByPasswordResetToken(String token) {
        return jpaRepository.findByPasswordResetToken(token).map(mapper::toDomain);
    }

    @Override
    public User save(User user) {
        return null;
    }
}