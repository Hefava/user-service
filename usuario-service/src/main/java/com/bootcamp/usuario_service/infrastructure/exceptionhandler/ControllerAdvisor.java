package com.bootcamp.usuario_service.infrastructure.exceptionhandler;

import com.bootcamp.usuario_service.domain.exception.InvalidCredentialsException;
import com.bootcamp.usuario_service.domain.exception.InvalidTokenException;
import com.bootcamp.usuario_service.domain.exception.MultipleUserValidationExceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bootcamp.usuario_service.domain.utils.UserValidationMessages.ERRORS;
import static com.bootcamp.usuario_service.domain.utils.UserValidationMessages.ERROR_INESPERADO;

@ControllerAdvice
public class ControllerAdvisor {

    private static final String MESSAGE = ERRORS;

    @ExceptionHandler(MultipleUserValidationExceptions.class)
    public ResponseEntity<Map<String, List<String>>> handleMultipleUserValidationExceptions(
            MultipleUserValidationExceptions multipleUserValidationExceptions) {
        Map<String, List<String>> response = new HashMap<>();
        response.put(MESSAGE, multipleUserValidationExceptions.getErrors());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<String> handleInvalidCredentials(InvalidCredentialsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<String> handleInvalidToken(InvalidTokenException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ERROR_INESPERADO);
    }
}
