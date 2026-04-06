package penelope.corretagem.penelopeapirest.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import penelope.corretagem.penelopeapirest.application.useCase.advertisement.ChangeAdvertisementStatusUseCase;
import penelope.corretagem.penelopeapirest.application.useCase.advertisement.CreateAdvertisementUseCase;
import penelope.corretagem.penelopeapirest.application.useCase.advertisement.GetAdvertisementByEstateIdUseCase;
import penelope.corretagem.penelopeapirest.application.useCase.advertisement.GetAllAdvertisementsUseCase;
import penelope.corretagem.penelopeapirest.application.useCase.advertisement.GetAdvertisementByIdUseCase;
import penelope.corretagem.penelopeapirest.application.useCase.advertisement.GetLatestAdvertisementUseCase;
import penelope.corretagem.penelopeapirest.application.useCase.advertisement.UpdateAdvertisementUseCase;
import penelope.corretagem.penelopeapirest.application.useCase.amenities.CreateAmenityUseCase;
import penelope.corretagem.penelopeapirest.application.useCase.amenities.DeleteAmenityUseCase;
import penelope.corretagem.penelopeapirest.application.useCase.amenities.GetAllAmenitiesUseCase;
import penelope.corretagem.penelopeapirest.application.useCase.amenities.GetAmenityByIdUseCase;
import penelope.corretagem.penelopeapirest.application.useCase.amenities.UpdateAmenityUseCase;
import penelope.corretagem.penelopeapirest.application.useCase.auth.AuthenticateUserUseCase;
import penelope.corretagem.penelopeapirest.application.useCase.email.SendContactEmailUseCase;
import penelope.corretagem.penelopeapirest.application.useCase.images.UploadImagesUseCase;
import penelope.corretagem.penelopeapirest.application.useCase.user.CreateUserUseCase;
import penelope.corretagem.penelopeapirest.application.useCase.user.DeleteUserUseCase;
import penelope.corretagem.penelopeapirest.application.useCase.user.GeneratePasswordResetTokenUseCase;
import penelope.corretagem.penelopeapirest.application.useCase.user.GetAllUsersUseCase;
import penelope.corretagem.penelopeapirest.application.useCase.user.GetUserAuthInfoUseCase;
import penelope.corretagem.penelopeapirest.application.useCase.user.GetUserByIdUseCase;
import penelope.corretagem.penelopeapirest.application.useCase.user.ResetPasswordUseCase;
import penelope.corretagem.penelopeapirest.application.useCase.user.UpdateUserUseCase;
import penelope.corretagem.penelopeapirest.application.useCase.user.ValidatePasswordResetTokenUseCase;
import penelope.corretagem.penelopeapirest.core.advertisement.repository.IAdvertisementRepository;
import penelope.corretagem.penelopeapirest.core.amenities.repository.IAmenitiesRepository;
import penelope.corretagem.penelopeapirest.core.gateway.IEmailGateway;
import penelope.corretagem.penelopeapirest.core.gateway.IEventTypeGateway;
import penelope.corretagem.penelopeapirest.core.gateway.IImageStorageGateway;
import penelope.corretagem.penelopeapirest.core.gateway.IPasswordEncoderGateway;
import penelope.corretagem.penelopeapirest.core.gateway.ITokenGateway;
import penelope.corretagem.penelopeapirest.core.user.repository.IUserRepository;

@Configuration
public class UseCaseConfig {

    @Bean
    public ChangeAdvertisementStatusUseCase changeAdvertisementStatusUseCase(IAdvertisementRepository repository) {
        return new ChangeAdvertisementStatusUseCase(repository);
    }

    @Bean
    public CreateAdvertisementUseCase createAdvertisementUseCase(
            IAdvertisementRepository advertisementRepository,
            IUserRepository userRepository,
            IEventTypeGateway eventTypeGateway
    ) {
        return new CreateAdvertisementUseCase(advertisementRepository, userRepository, eventTypeGateway);
    }

    @Bean
    public GetAdvertisementByEstateIdUseCase getAdvertisementByEstateIdUseCase(IAdvertisementRepository repository) {
        return new GetAdvertisementByEstateIdUseCase(repository);
    }

    @Bean
    public GetAdvertisementByIdUseCase getAdvertisementByIdUseCase(IAdvertisementRepository repository) {
        return new GetAdvertisementByIdUseCase(repository);
    }

    @Bean
    public GetAllAdvertisementsUseCase getAllAdvertisementsUseCase(IAdvertisementRepository repository) {
        return new GetAllAdvertisementsUseCase(repository);
    }

    @Bean
    public GetLatestAdvertisementUseCase getLatestAdvertisementUseCase(IAdvertisementRepository repository) {
        return new GetLatestAdvertisementUseCase(repository);
    }

    @Bean
    public UpdateAdvertisementUseCase updateAdvertisementUseCase(
            IAdvertisementRepository advertisementRepository,
            IEventTypeGateway eventTypeGateway,
            IUserRepository userRepository
    ) {
        return new UpdateAdvertisementUseCase(advertisementRepository, eventTypeGateway, userRepository);
    }

    @Bean
    public CreateAmenityUseCase createAmenityUseCase(IAmenitiesRepository repository) {
        return new CreateAmenityUseCase(repository);
    }

    @Bean
    public DeleteAmenityUseCase deleteAmenityUseCase(IAmenitiesRepository repository) {
        return new DeleteAmenityUseCase(repository);
    }

    @Bean
    public GetAllAmenitiesUseCase getAllAmenitiesUseCase(IAmenitiesRepository repository) {
        return new GetAllAmenitiesUseCase(repository);
    }

    @Bean
    public GetAmenityByIdUseCase getAmenityByIdUseCase(IAmenitiesRepository repository) {
        return new GetAmenityByIdUseCase(repository);
    }

    @Bean
    public UpdateAmenityUseCase updateAmenityUseCase(IAmenitiesRepository repository) {
        return new UpdateAmenityUseCase(repository);
    }

    @Bean
    public AuthenticateUserUseCase authenticateUserUseCase(
            IUserRepository userRepository,
            IPasswordEncoderGateway passwordEncoderGateway,
            ITokenGateway tokenGateway
    ) {
        return new AuthenticateUserUseCase(userRepository, passwordEncoderGateway, tokenGateway);
    }

    @Bean
    public SendContactEmailUseCase sendContactEmailUseCase(IEmailGateway emailGateway) {
        return new SendContactEmailUseCase(emailGateway);
    }

    @Bean
    public UploadImagesUseCase uploadImagesUseCase(IImageStorageGateway imageStorageGateway) {
        return new UploadImagesUseCase(imageStorageGateway);
    }

    @Bean
    public CreateUserUseCase createUserUseCase(IUserRepository userRepository, IPasswordEncoderGateway passwordEncoder) {
        return new CreateUserUseCase(userRepository, passwordEncoder);
    }

    @Bean
    public DeleteUserUseCase deleteUserUseCase(IUserRepository userRepository) {
        return new DeleteUserUseCase(userRepository);
    }

    @Bean
    public GeneratePasswordResetTokenUseCase generatePasswordResetTokenUseCase(
            IUserRepository userRepository,
            IEmailGateway emailGateway
    ) {
        return new GeneratePasswordResetTokenUseCase(userRepository, emailGateway);
    }

    @Bean
    public GetAllUsersUseCase getAllUsersUseCase(IUserRepository userRepository) {
        return new GetAllUsersUseCase(userRepository);
    }

    @Bean
    public GetUserAuthInfoUseCase getUserAuthInfoUseCase(IUserRepository userRepository, ITokenGateway tokenGateway) {
        return new GetUserAuthInfoUseCase(userRepository, tokenGateway);
    }

    @Bean
    public GetUserByIdUseCase getUserByIdUseCase(IUserRepository userRepository) {
        return new GetUserByIdUseCase(userRepository);
    }

    @Bean
    public ResetPasswordUseCase resetPasswordUseCase(
            IUserRepository userRepository,
            IPasswordEncoderGateway passwordEncoder
    ) {
        return new ResetPasswordUseCase(userRepository, passwordEncoder);
    }

    @Bean
    public UpdateUserUseCase updateUserUseCase(
            IUserRepository userRepository,
            IPasswordEncoderGateway passwordEncoder
    ) {
        return new UpdateUserUseCase(userRepository, passwordEncoder);
    }

    @Bean
    public ValidatePasswordResetTokenUseCase validatePasswordResetTokenUseCase(IUserRepository userRepository) {
        return new ValidatePasswordResetTokenUseCase(userRepository);
    }
}