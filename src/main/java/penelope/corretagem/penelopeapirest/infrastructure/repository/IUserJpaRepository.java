package penelope.corretagem.penelopeapirest.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import penelope.corretagem.penelopeapirest.infrastructure.entity.UserJpaEntity;

import java.util.Optional;

public interface IUserJpaRepository extends JpaRepository<UserJpaEntity, Long> {

    Optional<UserJpaEntity> findByEmail(String email);
    Optional<UserJpaEntity> findByCpf(String cpf);
    Optional<UserJpaEntity> findByCreci(String creci);

    Optional<UserJpaEntity> findByPasswordResetToken(String token);
}