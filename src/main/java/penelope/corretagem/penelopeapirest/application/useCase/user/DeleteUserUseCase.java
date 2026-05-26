package penelope.corretagem.penelopeapirest.application.useCase.user;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.core.context.SecurityContextHolder;
import penelope.corretagem.penelopeapirest.config.CacheNames;
import penelope.corretagem.penelopeapirest.core.user.repository.IUserRepository;
import penelope.corretagem.penelopeapirest.core.exception.ResourceNotFoundException;
import penelope.corretagem.penelopeapirest.core.user.valueObject.AccessLevel;

public class DeleteUserUseCase {

    private final IUserRepository userRepository;

    public DeleteUserUseCase(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Caching(evict = {
            @CacheEvict(value = CacheNames.USER, key = "#id"),
            @CacheEvict(value = CacheNames.USERS, allEntries = true)
    })
    public void execute(Long id) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var currentUserEmail = authentication.getName();

        boolean isAdministrador = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority()
                        .equals("ROLE_" + AccessLevel.ADMINISTRADOR.name()));

        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        if (!isAdministrador && !user.getEmail().equals(currentUserEmail)) {
            throw new ResourceNotFoundException("Usuário não encontrado");
        }

        userRepository.deleteById(id);
    }
}