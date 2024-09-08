package com.bootcamp.usuario_service.domain.utils;

public class UserValidationMessages {

  private UserValidationMessages() {
    throw new AssertionError("Cannot instantiate this class");
  }

  public static final String ERROR_INESPERADO = "Ocurrió un error inesperado";
  public static final String INVALID_EMAIL_FORMAT = "Correo no tiene un formato válido";
  public static final String INVALID_ID_DOCUMENT = "Documento de identidad debe ser numérico";
  public static final String CELLULAR_LENGTH_EXCEEDED = "El teléfono no debe exceder 13 caracteres";
  public static final String USER_UNDERAGE = "El usuario debe ser mayor de edad";
  public static final String USER_ALREADY_EXISTS = "Ya existe un usuario con el documento de identidad proporcionado";
  public static final String USUARIO_NO_ENCONTRADO = "Usuario no encontrado";
  public static final String USUARIO_CREADO_EXITO = "Usuario creado exitosamente";
  public static final String SOLICITUD_INCORRECTA = "Solicitud incorrecta, por ejemplo, datos de usuario inválidos";
  public static final String ERROR_INTERNO_SERVIDOR = "Error interno del servidor";
  public static final String INICIO_SESION_EXITOSO = "Inicio de sesión exitoso";
  public static final String CREDENCIALES_INCORRECTAS = "Usuario o contraseña incorrectos";
  public static final String ACCESO_DENEGADO = "Acceso denegado: No tienes permiso para acceder a este recurso.";
  public static final String TOKEN_INVALIDO = "Token inválido o expirado";
  public static final String ERROR_AUTENTICACION = "Error en la autenticación";

  //Info Swagger
  public static final String AUTH_SUMMARY_INICIAR_SESION = "Iniciar sesión";
  public static final String AUTH_DESCRIPTION_INICIAR_SESION = "Autentica a un usuario en el sistema y retorna un token JWT.";
  public static final String REGISTER_SUMMARY_REGISTRAR_USUARIO = "Registrar un nuevo usuario";
  public static final String REGISTER_DESCRIPTION_REGISTRAR_USUARIO = "Crea un nuevo usuario auxiliar de bodega en el sistema.";
}
