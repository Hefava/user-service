package com.bootcamp.usuario_service.domain.utils;

public class SecurityConstants {
    public static final String ROL_ADMIN = "ADMIN";
    public static final String ROL_AUX_BODEGA = "AUX_BODEGA";
    public static final String ROL_CLIENTE = "CLIENTE";

    private SecurityConstants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
