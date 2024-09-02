package com.bootcamp.usuario_service.domain.utils;

public class UserValidationMessages {

  private UserValidationMessages() {
    throw new AssertionError("Cannot instantiate this class");
  }

  public static final String INVALID_EMAIL_FORMAT = "Correo no tiene un formato válido";
  public static final String INVALID_ID_DOCUMENT = "Documento de identidad debe ser numérico";
  public static final String CELLULAR_LENGTH_EXCEEDED = "El teléfono no debe exceder 13 caracteres";
  public static final String USER_UNDERAGE = "El usuario debe ser mayor de edad";
  public static final String USER_ALREADY_EXISTS = "Ya existe un usuario con el documento de identidad proporcionado";
}
