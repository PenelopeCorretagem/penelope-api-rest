package penelope.corretagem.penelopeapirest.infrastructure.web.exception;

import java.time.LocalDateTime;

public record StandardErrorResponse(
        Integer status,
        String message,
        LocalDateTime timestamp
) {
}