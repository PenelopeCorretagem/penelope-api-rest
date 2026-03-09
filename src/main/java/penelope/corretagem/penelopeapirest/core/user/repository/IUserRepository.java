package penelope.corretagem.penelopeapirest.core.user.repository;


import penelope.corretagem.penelopeapirest.core.user.User;
import java.util.Optional;

public interface IUserRepository {

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);
    Optional<User> findByPasswordResetToken(String token);

    User save(User user);
}
