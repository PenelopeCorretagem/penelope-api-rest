package penelope.corretagem.penelopeapirest.application.useCase.user;

import org.springframework.security.core.context.SecurityContextHolder;
import penelope.corretagem.penelopeapirest.application.dto.UserResponse;
import penelope.corretagem.penelopeapirest.application.dto.UserUpdateRequest;
import penelope.corretagem.penelopeapirest.core.exception.DomainValidationException;
import penelope.corretagem.penelopeapirest.core.exception.ForbiddenOperationException;
import penelope.corretagem.penelopeapirest.core.exception.ResourceNotFoundException;
import penelope.corretagem.penelopeapirest.core.gateway.IPasswordEncoderGateway;
import penelope.corretagem.penelopeapirest.core.user.repository.IUserRepository;
import penelope.corretagem.penelopeapirest.core.user.valueObject.AccessLevel;

public class UpdateUserUseCase {

    private final IUserRepository userRepository;
    private final IPasswordEncoderGateway passwordEncoder;

    public UpdateUserUseCase(IUserRepository userRepository, IPasswordEncoderGateway passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse execute(Long id, UserUpdateRequest req) {

        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var currentUserEmail = authentication.getName();
        boolean isAdministrador = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority()
                        .equals("ROLE_" + AccessLevel.ADMINISTRADOR.name()));

        boolean isOwner = user.getEmail().equals(currentUserEmail);
        if (!isAdministrador && !isOwner) {
            throw new ForbiddenOperationException("Você não tem permissão para atualizar este usuário");
        }

        if (req.email() != null && req.email().isBlank()) {
            throw new DomainValidationException("O e-mail informado não pode ser vazio");
        }

        if (req.email() != null && !req.email().isBlank()) {
            userRepository.findByEmail(req.email())
                    .filter(existingUser -> !existingUser.getId().equals(user.getId()))
                    .ifPresent(existingUser -> {
                        throw new DomainValidationException("O e-mail informado já está cadastrado");
                    });
        }

        if (req.cpf() != null && !req.cpf().isBlank()) {
            userRepository.findByCpf(req.cpf())
                    .filter(existingUser -> !existingUser.getId().equals(user.getId()))
                    .ifPresent(existingUser -> {
                        throw new DomainValidationException("O CPF informado já está cadastrado");
                    });
        }

        if (req.creci() != null && !req.creci().isBlank()) {
            userRepository.findByCreci(req.creci())
                    .filter(existingUser -> !existingUser.getId().equals(user.getId()))
                    .ifPresent(existingUser -> {
                        throw new DomainValidationException("O CRECI informado já está cadastrado");
                    });
        }

        AccessLevel requestedAccessLevel = null;
        if (req.accessLevel() != null) {
            try {
                requestedAccessLevel = AccessLevel.fromExternalValue(req.accessLevel());
            } catch (IllegalArgumentException ex) {
                throw new DomainValidationException("Nivel de acesso invalido: " + req.accessLevel());
            }

            if (!isAdministrador && requestedAccessLevel != user.getAccessLevel()) {
                throw new ForbiddenOperationException("Você não pode alterar o nível de acesso do usuário");
            }
        }

        user.updateInformation(
                req.name(), req.email(), req.cpf(),
                req.dateBirth(), req.monthlyIncome(), req.phone()
        );

        user.updateAccessProfile(requestedAccessLevel, req.creci());

        if (req.password() != null && !req.password().isBlank()) {
            user.changePassword(passwordEncoder.encode(req.password()));
        }

        var updatedUser = userRepository.update(user);

        return new UserResponse(
                updatedUser.getId(), updatedUser.getName(), updatedUser.getEmail(),
                updatedUser.getCpf(), updatedUser.getDateBirth(), updatedUser.getMonthlyIncome(),
            updatedUser.getPhone(), updatedUser.getCreci(), updatedUser.getAccessLevel().toExternalValue(), updatedUser.isActive()
        );
    }
}
