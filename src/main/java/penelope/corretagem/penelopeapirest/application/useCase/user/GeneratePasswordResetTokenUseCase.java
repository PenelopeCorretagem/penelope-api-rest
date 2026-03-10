package penelope.corretagem.penelopeapirest.application.useCase.user;

import org.springframework.stereotype.Service;
import penelope.corretagem.penelopeapirest.core.gateway.IEmailGateway;
import penelope.corretagem.penelopeapirest.core.user.repository.IUserRepository;

import java.security.SecureRandom;
import java.util.Date;

@Service
public class GeneratePasswordResetTokenUseCase {

    private final IUserRepository userRepository;
    private final IEmailGateway emailGateway;

    private final SecureRandom secureRandom = new SecureRandom();

    public GeneratePasswordResetTokenUseCase(IUserRepository userRepository, IEmailGateway emailGateway) {
        this.userRepository = userRepository;
        this.emailGateway = emailGateway;
    }

    public void execute(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {

            String token = String.format("%06d", secureRandom.nextInt(1000000));

            Date expiryDate = new Date(System.currentTimeMillis() + 3600_000); // 1 hora

            user.generatePasswordResetToken(token, expiryDate);

            userRepository.update(user);
            emailGateway.sendPasswordResetEmail(user.getEmail(), token);
        });
    }
}