package com.bootcamp.usuario_service.Usuario.domain.api.usecase;

import com.bootcamp.usuario_service.domain.api.usecase.RegistrarUseCase;
import com.bootcamp.usuario_service.domain.exception.MultipleUserValidationExceptions;
import com.bootcamp.usuario_service.domain.model.Usuario;
import com.bootcamp.usuario_service.domain.spi.IEncryptPasswordPort;
import com.bootcamp.usuario_service.domain.spi.IUsuarioPersistencePort;
import com.bootcamp.usuario_service.domain.utils.UserValidationMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RegistrarUseCaseTest {

    @Mock
    private IUsuarioPersistencePort usuarioPersistencePort;

    @Mock
    private IEncryptPasswordPort encryptPasswordPort;

    @InjectMocks
    private RegistrarUseCase registrarUsuarioUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registrarUsuario_WhenUserAlreadyExists_ShouldThrowMultipleUserValidationExceptions() {
        Usuario usuario = new Usuario();
        usuario.setDocumentoDeIdentidad("12345678");
        usuario.setCorreo("valid.email@example.com");
        usuario.setCelular("1234567890");
        usuario.setFechaNacimiento(LocalDate.now().minusYears(20));

        when(usuarioPersistencePort.existsByCorreo(usuario.getCorreo())).thenReturn(true);

        MultipleUserValidationExceptions exception = assertThrows(MultipleUserValidationExceptions.class, () ->
                registrarUsuarioUseCase.registrarAuxBodega(usuario)
        );

        List<String> expectedErrors = Arrays.asList(UserValidationMessages.USER_ALREADY_EXISTS);
        assertEquals(expectedErrors, exception.getErrors());

        verify(usuarioPersistencePort, never()).saveUsuario(any(Usuario.class));
    }

    @Test
    void registrarUsuario_WhenInvalidEmailFormat_ShouldThrowMultipleUserValidationExceptions() {
        Usuario usuario = new Usuario();
        usuario.setCorreo("invalid-email-format");
        usuario.setDocumentoDeIdentidad("12345678");
        usuario.setCelular("+1234567890");
        usuario.setFechaNacimiento(LocalDate.now().minusYears(20));

        when(usuarioPersistencePort.existsByCorreo(usuario.getCorreo())).thenReturn(false);

        MultipleUserValidationExceptions exception = assertThrows(MultipleUserValidationExceptions.class, () ->
                registrarUsuarioUseCase.registrarAuxBodega(usuario)
        );

        List<String> expectedErrors = Arrays.asList(UserValidationMessages.INVALID_EMAIL_FORMAT);
        assertEquals(expectedErrors, exception.getErrors());

        verify(usuarioPersistencePort, never()).saveUsuario(any(Usuario.class));
    }

    @Test
    void registrarUsuario_WhenInvalidIdDocument_ShouldThrowMultipleUserValidationExceptions() {
        Usuario usuario = new Usuario();
        usuario.setCorreo("valid.email@example.com");
        usuario.setDocumentoDeIdentidad("invalid-id");
        usuario.setCelular("1234567890");
        usuario.setFechaNacimiento(LocalDate.now().minusYears(20));

        when(usuarioPersistencePort.existsByCorreo(usuario.getCorreo())).thenReturn(false);

        MultipleUserValidationExceptions exception = assertThrows(MultipleUserValidationExceptions.class, () ->
                registrarUsuarioUseCase.registrarAuxBodega(usuario)
        );

        List<String> expectedErrors = Arrays.asList(UserValidationMessages.INVALID_ID_DOCUMENT);
        assertEquals(expectedErrors, exception.getErrors());

        verify(usuarioPersistencePort, never()).saveUsuario(any(Usuario.class));
    }

    @Test
    void registrarUsuario_WhenCellularLengthExceeded_ShouldThrowMultipleUserValidationExceptions() {
        Usuario usuario = new Usuario();
        usuario.setCorreo("valid.email@example.com");
        usuario.setDocumentoDeIdentidad("12345678");
        usuario.setCelular("12345678901234"); // Exceeds maximum length
        usuario.setFechaNacimiento(LocalDate.now().minusYears(20));

        when(usuarioPersistencePort.existsByCorreo(usuario.getCorreo())).thenReturn(false);

        MultipleUserValidationExceptions exception = assertThrows(MultipleUserValidationExceptions.class, () ->
                registrarUsuarioUseCase.registrarAuxBodega(usuario)
        );

        List<String> expectedErrors = Arrays.asList(UserValidationMessages.CELLULAR_LENGTH_EXCEEDED);
        assertEquals(expectedErrors, exception.getErrors());

        verify(usuarioPersistencePort, never()).saveUsuario(any(Usuario.class));
    }

    @Test
    void registrarUsuario_WhenUserUnderage_ShouldThrowMultipleUserValidationExceptions() {
        Usuario usuario = new Usuario();
        usuario.setCorreo("valid.email@example.com");
        usuario.setDocumentoDeIdentidad("12345678");
        usuario.setCelular("1234567890");
        usuario.setFechaNacimiento(LocalDate.now().minusYears(10));

        when(usuarioPersistencePort.existsByCorreo(usuario.getCorreo())).thenReturn(false);

        MultipleUserValidationExceptions exception = assertThrows(MultipleUserValidationExceptions.class, () ->
                registrarUsuarioUseCase.registrarAuxBodega(usuario)
        );

        List<String> expectedErrors = Arrays.asList(UserValidationMessages.USER_UNDERAGE);
        assertEquals(expectedErrors, exception.getErrors());

        verify(usuarioPersistencePort, never()).saveUsuario(any(Usuario.class));
    }

    @Test
    void registrarUsuarioAuxiliar_WhenValidUsuario_ShouldSaveUsuarioSuccessfully() {
        Usuario usuario = new Usuario();
        usuario.setCorreo("valid.email@example.com");
        usuario.setDocumentoDeIdentidad("12345678");
        usuario.setCelular("1234567890");
        usuario.setFechaNacimiento(LocalDate.now().minusYears(20));

        when(usuarioPersistencePort.existsByCorreo(usuario.getCorreo())).thenReturn(false);
        when(encryptPasswordPort.encrypt(anyString())).thenReturn("encryptedPassword");

        registrarUsuarioUseCase.registrarAuxBodega(usuario);

        verify(usuarioPersistencePort).saveUsuario(usuario);
    }

    @Test
    void registrarUsuarioCliente_WhenValidUsuario_ShouldSaveUsuarioSuccessfully() {
        Usuario usuario = new Usuario();
        usuario.setCorreo("valid.email@example.com");
        usuario.setDocumentoDeIdentidad("12345678");
        usuario.setCelular("1234567890");
        usuario.setFechaNacimiento(LocalDate.now().minusYears(20));

        when(usuarioPersistencePort.existsByCorreo(usuario.getCorreo())).thenReturn(false);
        when(encryptPasswordPort.encrypt(anyString())).thenReturn("encryptedPassword");

        registrarUsuarioUseCase.registrarCliente(usuario);

        verify(usuarioPersistencePort).saveUsuario(usuario);
    }
}