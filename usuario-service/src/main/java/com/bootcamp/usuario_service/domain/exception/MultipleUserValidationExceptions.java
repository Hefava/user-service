package com.bootcamp.usuario_service.domain.exception;
import java.util.List;

public class MultipleUserValidationExceptions extends RuntimeException {

    private final List<String> errors;

    public MultipleUserValidationExceptions(List<String> errors) {
        super("Se han producido múltiples errores de validación de usuario");
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
