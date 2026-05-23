package penelope.corretagem.penelopeapirest.infrastructure.adapter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import penelope.corretagem.penelopeapirest.application.dto.ForgotPasswordRequest;
import penelope.corretagem.penelopeapirest.application.dto.LoginRequest;
import penelope.corretagem.penelopeapirest.application.dto.LoginResponse;
import penelope.corretagem.penelopeapirest.application.dto.ResetPasswordRequest;
import penelope.corretagem.penelopeapirest.application.dto.ValidateTokenRequest;
import penelope.corretagem.penelopeapirest.core.exception.DomainValidationException;
import penelope.corretagem.penelopeapirest.core.exception.IntegrationException;
import penelope.corretagem.penelopeapirest.core.exception.InvalidCredentialsException;
import penelope.corretagem.penelopeapirest.core.gateway.IAuthGateway;

@Component
public class AuthServiceGatewayAdapter implements IAuthGateway {

    private record AuthServiceLoginResponse(String token, Long id, int accessLevel, String accessLevelLabel) {}

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public AuthServiceGatewayAdapter(@Qualifier("authServiceRestClient") RestClient restClient,
                                     ObjectMapper objectMapper) {
        this.restClient = restClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public LoginResponse authenticate(LoginRequest request) {
        try {
            AuthServiceLoginResponse response = restClient.post()
                .uri("/api/v1/auth/login")
                .body(request)
                .retrieve()
                .body(AuthServiceLoginResponse.class);

            if (response == null) {
                throw new IntegrationException("Resposta vazia ao autenticar no auth-service");
            }

            return new LoginResponse(response.token(), response.id(), response.accessLevelLabel());
        } catch (HttpClientErrorException.Unauthorized ex) {
            throw new InvalidCredentialsException();
        } catch (RestClientResponseException ex) {
            throw mapClientException("Falha ao autenticar no auth-service", ex);
        } catch (Exception ex) {
            throw new IntegrationException("Erro inesperado ao autenticar no auth-service", ex);
        }
    }

    @Override
    public void forgotPassword(ForgotPasswordRequest request) {
        try {
            restClient.post()
                .uri("/api/v1/auth/forgot-password")
                .body(request)
                .retrieve()
                .toBodilessEntity();
        } catch (RestClientResponseException ex) {
            throw mapClientException("Falha ao solicitar recuperacao de senha", ex);
        } catch (Exception ex) {
            throw new IntegrationException("Erro inesperado ao solicitar recuperacao de senha", ex);
        }
    }

    @Override
    public void validateResetToken(ValidateTokenRequest request) {
        try {
            restClient.post()
                .uri("/api/v1/auth/validate-reset-token")
                .body(request)
                .retrieve()
                .toBodilessEntity();
        } catch (RestClientResponseException ex) {
            throw mapClientException("Falha ao validar token de redefinicao", ex);
        } catch (Exception ex) {
            throw new IntegrationException("Erro inesperado ao validar token de redefinicao", ex);
        }
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) {
        try {
            restClient.post()
                .uri("/api/v1/auth/reset-password")
                .body(request)
                .retrieve()
                .toBodilessEntity();
        } catch (RestClientResponseException ex) {
            throw mapClientException("Falha ao redefinir senha", ex);
        } catch (Exception ex) {
            throw new IntegrationException("Erro inesperado ao redefinir senha", ex);
        }
    }

    private RuntimeException mapClientException(String fallbackMessage, RestClientResponseException ex) {
        HttpStatusCode statusCode = ex.getStatusCode();

        if (statusCode.is4xxClientError()) {
            if (statusCode.value() == 401) {
                return new InvalidCredentialsException();
            }
            return new DomainValidationException(extractErrorMessage(ex.getResponseBodyAsString(), fallbackMessage));
        }

        return new IntegrationException(fallbackMessage, ex);
    }

    private String extractErrorMessage(String responseBody, String fallbackMessage) {
        if (responseBody == null || responseBody.isBlank()) {
            return fallbackMessage;
        }

        try {
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            if (jsonNode.hasNonNull("message")) {
                return jsonNode.get("message").asText();
            }
        } catch (Exception ignored) {
            return fallbackMessage;
        }

        return fallbackMessage;
    }
}
