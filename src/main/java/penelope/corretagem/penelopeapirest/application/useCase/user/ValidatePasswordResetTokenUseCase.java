package penelope.corretagem.penelopeapirest.application.useCase.user;

import penelope.corretagem.penelopeapirest.core.exception.DomainValidationException;
import penelope.corretagem.penelopeapirest.core.user.repository.IUserRepository;

import java.util.Date;

public class ValidatePasswordResetTokenUseCase {

    private final IUserRepository userRepository;

    public ValidatePasswordResetTokenUseCase(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(String token) {
        var user = userRepository.findByPasswordResetToken(token)
                .orElseThrow(() -> new DomainValidationException("Token inválido ou não encontrado."));

        if (user.getPasswordResetTokenExpiry() == null || user.getPasswordResetTokenExpiry().before(new Date())) {
            throw new DomainValidationException("Token expirado. Por favor, solicite um novo código.");
        }
    }
}