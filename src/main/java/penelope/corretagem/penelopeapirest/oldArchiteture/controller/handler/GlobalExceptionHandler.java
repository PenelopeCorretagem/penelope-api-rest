//package penelope.corretagem.penelopeapirest.controller.handler;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import penelope.corretagem.penelopeapirest.data.domain.enums.Error;
//import penelope.corretagem.penelopeapirest.service.exception.*;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
//        Map<String, String> errors = new HashMap<>();
//
//        ex.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
//
//        return errors;
//    }
//
//    @ExceptionHandler(InvalidTokenException.class)
//    public ResponseEntity<Map<String, String>> handleInvalidToken(InvalidTokenException ex) {
//        Map<String, String> errorResponse = Map.of(
//          Error.MESSAGE.getField(), ex.getMessage(),
//          Error.MESSAGE.getField(), String.valueOf(HttpStatus.BAD_REQUEST.value())
//        );
//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST); // 400 Bad Request
//    }
//
//    @ExceptionHandler(UserNotFoundException.class)
//    public ResponseEntity<Map<String, String>> handleUserNotFound(UserNotFoundException ex) {
//        Map<String, String> errorResponse = Map.of(
//          Error.MESSAGE.getField(), ex.getMessage(),
//          Error.MESSAGE.getField(), String.valueOf(HttpStatus.NOT_FOUND.value())
//        );
//        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(EstateNotFoundException.class)
//    public ResponseEntity<Map<String, String>> handleEstateNotFound(EstateNotFoundException ex) {
//        Map<String, String> errorResponse = Map.of(
//          Error.MESSAGE.getField(), ex.getMessage(),
//          Error.MESSAGE.getField(), String.valueOf(HttpStatus.NOT_FOUND.value())
//        );
//        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(UserEmailAlreadyExistsException.class)
//    public ResponseEntity<Map<String, String>> handleEmailAlreadyExists(UserEmailAlreadyExistsException ex) {
//        Map<String, String> errorResponse = Map.of(
//          Error.MESSAGE.getField(), ex.getMessage(),
//          Error.MESSAGE.getField(), String.valueOf(HttpStatus.CONFLICT.value())
//        );
//        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
//    }
//}