package penelope.corretagem.penelopeapirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import penelope.corretagem.penelopeapirest.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
