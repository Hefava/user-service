package com.bootcamp.usuario_service.domain.utils;

public class UsuarioUtils {

    // Patrón de correo electrónico
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

    // Patrón para el documento de identidad
    public static final String DOCUMENTO_REGEX = "\\d+";

    // Longitud máxima para el celular
    public static final int MAX_CELULAR_LENGTH = 13;

    // Edad mínima para ser mayor de edad
    public static final int MIN_AGE = 18;

    // ID de rol auxiliar de bodega
    public static final Long ROL_AUX_BODEGA_ID = 2L;

    // ID de rol Cliente
    public static final Long ROL_CLIENTE_ID = 3L;



    private UsuarioUtils() {
        throw new AssertionError("Cannot instantiate this class");
    }
}