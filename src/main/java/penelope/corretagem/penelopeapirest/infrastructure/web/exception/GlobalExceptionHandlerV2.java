package penelope.corretagem.penelopeapirest.infrastructure.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import penelope.corretagem.penelopeapirest.core.exception.ResourceNotFoundException;

import java.time.LocalDateTime;

@RestControllerAdvice("globalExceptionHandlerV2")
public class GlobalExceptionHandlerV2 {

    // 1. Tratamento para quando um recurso não é encontrado (Erro 404)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {

        StandardErrorResponse error = new StandardErrorResponse(
                HttpStatus.NOT_FOUND.value(), // 404
                ex.getMessage(),              // A mensagem que veio do Use Case
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // 2. Tratamento genérico para argumentos inválidos (Erro 400)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<StandardErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {

        StandardErrorResponse error = new StandardErrorResponse(
                HttpStatus.BAD_REQUEST.value(), // 400
                ex.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // 3. Fallback para qualquer outro erro inesperado (Erro 500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardErrorResponse> handleGenericException(Exception ex) {

        StandardErrorResponse error = new StandardErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), // 500
                "Ocorreu um erro interno no servidor.", // Esconde o erro real do usuário por segurança
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}