package penelope.corretagem.penelopeapirest.core.gateway;

import penelope.corretagem.penelopeapirest.application.dto.ForgotPasswordRequest;
import penelope.corretagem.penelopeapirest.application.dto.LoginRequest;
import penelope.corretagem.penelopeapirest.application.dto.LoginResponse;
import penelope.corretagem.penelopeapirest.application.dto.ResetPasswordRequest;
import penelope.corretagem.penelopeapirest.application.dto.ValidateTokenRequest;

public interface IAuthGateway {

    LoginResponse authenticate(LoginRequest request);

    void forgotPassword(ForgotPasswordRequest request);

    void validateResetToken(ValidateTokenRequest request);

    void resetPassword(ResetPasswordRequest request);
}
