package penelope.corretagem.penelopeapirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import penelope.corretagem.penelopeapirest.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
}
