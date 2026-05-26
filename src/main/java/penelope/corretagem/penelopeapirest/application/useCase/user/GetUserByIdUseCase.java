package penelope.corretagem.penelopeapirest.application.useCase.user;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import penelope.corretagem.penelopeapirest.application.dto.UserResponse;
import penelope.corretagem.penelopeapirest.config.CacheNames;
import penelope.corretagem.penelopeapirest.core.exception.ResourceNotFoundException;
import penelope.corretagem.penelopeapirest.core.user.repository.IUserRepository;
import penelope.corretagem.penelopeapirest.core.user.valueObject.AccessLevel;

public class GetUserByIdUseCase {

    private final IUserRepository userRepository;

    public GetUserByIdUseCase(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Cacheable(value = CacheNames.USER, key = "#id")
    public UserResponse execute(Long id) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var currentUserEmail = authentication.getName();

        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        boolean canViewOtherProfiles = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority()
                        .equals("ROLE_" + AccessLevel.ADMINISTRADOR.name())
                        || grantedAuthority.getAuthority().equals("ROLE_" + AccessLevel.CORRETOR.name()));

        if (!canViewOtherProfiles && !user.getEmail().equals(currentUserEmail)) {
            throw new ResourceNotFoundException("Usuário não encontrado");
        }

        return new UserResponse(
                user.getId(), user.getName(), user.getEmail(),
                user.getCpf(), user.getDateBirth(), user.getMonthlyIncome(),
            user.getPhone(), user.getCreci(), user.getAccessLevel().toExternalValue(), user.isActive()
        );
    }
}
