package penelope.corretagem.penelopeapirest.application.useCase.user;

import org.springframework.stereotype.Service;
import penelope.corretagem.penelopeapirest.core.gateway.IPasswordEncoderGateway;
import penelope.corretagem.penelopeapirest.core.user.repository.IUserRepository;
import penelope.corretagem.penelopeapirest.oldArchiteture.service.exception.InvalidTokenException;

@Service
public class ResetPasswordUseCase {

    private final IUserRepository userRepository;
    private final IPasswordEncoderGateway passwordEncoder;

    public ResetPasswordUseCase(IUserRepository userRepository, IPasswordEncoderGateway passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void execute(String token, String newPassword) {
        var user = userRepository.findByPasswordResetToken(token)
                .orElseThrow(() -> new InvalidTokenException("Token inválido ou não encontrado."));

        user.applyNewPassword(passwordEncoder.encode(newPassword));

        userRepository.update(user);
    }
}