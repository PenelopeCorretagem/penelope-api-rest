package penelope.corretagem.penelopeapirest.infrastructure.adapter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import penelope.corretagem.penelopeapirest.application.dto.ValidateAccessTokenRequest;
import penelope.corretagem.penelopeapirest.application.dto.ValidateAccessTokenResponse;
import penelope.corretagem.penelopeapirest.core.exception.DomainValidationException;
import penelope.corretagem.penelopeapirest.core.exception.IntegrationException;
import penelope.corretagem.penelopeapirest.core.exception.InvalidCredentialsException;
import penelope.corretagem.penelopeapirest.core.gateway.ITokenGateway;
import penelope.corretagem.penelopeapirest.core.gateway.TokenValidationResult;

@Component
public class TokenGatewayAdapter implements ITokenGateway {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public TokenGatewayAdapter(@Qualifier("authServiceRestClient") RestClient restClient,
                               ObjectMapper objectMapper) {
        this.restClient = restClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public TokenValidationResult validateToken(String token) {
        try {
            ValidateAccessTokenResponse response = restClient.post()
                .uri("/api/v1/auth/validate-access-token")
                .body(new ValidateAccessTokenRequest(token))
                .retrieve()
                .body(ValidateAccessTokenResponse.class);

                    if (response == null
                        || response.email() == null
                        || response.email().isBlank()
                        || response.accessLevel() == null
                        || response.accessLevel().isBlank()) {
                throw new InvalidCredentialsException();
            }

            return new TokenValidationResult(response.email(), response.accessLevel());
        } catch (HttpClientErrorException.Unauthorized ex) {
            throw new InvalidCredentialsException();
        } catch (RestClientResponseException ex) {
            throw mapClientException("Falha ao validar token no auth-service", ex);
        } catch (Exception ex) {
            throw new IntegrationException("Erro inesperado ao validar token no auth-service", ex);
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