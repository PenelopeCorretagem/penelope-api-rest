package penelope.corretagem.penelopeapirest.core.user.repository;


import penelope.corretagem.penelopeapirest.core.user.User;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {
    List<User> findAll();
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    Optional<User> findByCpf(String cpf);
    Optional<User> findByCreci(String creci);

    User save(User user);
    User update(User user);

    boolean existsById(Long id);
    void deleteById(Long id);

    Optional<User> findByPasswordResetToken(String token);
}
