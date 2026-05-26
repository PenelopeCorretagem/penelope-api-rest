package penelope.corretagem.penelopeapirest.application.useCase.user;

import org.springframework.security.core.context.SecurityContextHolder;
import penelope.corretagem.penelopeapirest.core.exception.ForbiddenOperationException;
import penelope.corretagem.penelopeapirest.core.user.repository.IUserRepository;
import penelope.corretagem.penelopeapirest.core.exception.ResourceNotFoundException;
import penelope.corretagem.penelopeapirest.core.user.valueObject.AccessLevel;

public class DeleteUserUseCase {

    private final IUserRepository userRepository;

    public DeleteUserUseCase(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(Long id) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var currentUserEmail = authentication.getName();

        boolean isAdministrador = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority()
                        .equals("ROLE_" + AccessLevel.ADMINISTRADOR.name()));

        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        if (!isAdministrador && !user.getEmail().equals(currentUserEmail)) {
            throw new ForbiddenOperationException("Você não tem permissão para excluir este usuário");
        }

        userRepository.deleteById(id);
    }
}
