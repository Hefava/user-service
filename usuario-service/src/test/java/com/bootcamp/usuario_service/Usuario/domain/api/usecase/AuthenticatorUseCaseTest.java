package com.bootcamp.usuario_service.Usuario.domain.api.usecase;

import com.bootcamp.usuario_service.domain.api.usecase.AuthenticatorUseCase;
import com.bootcamp.usuario_service.domain.model.Usuario;
import com.bootcamp.usuario_service.domain.spi.IAuthenticationPort;
import com.bootcamp.usuario_service.domain.utils.Validation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class AuthenticatorUseCaseTest {

    @Mock
    private IAuthenticationPort authenticationPort;

    @InjectMocks
    private AuthenticatorUseCase authenticatorUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_WhenUserValid_ShouldReturnToken() {
        // Arrange
        String email = "test@correo.com";
        String password = "password123";
        Usuario usuario = new Usuario();
        usuario.setCorreo(email);
        usuario.setClave(password);

        when(authenticationPort.authenticate(email, password)).thenReturn(usuario);
        when(authenticationPort.generateToken(usuario)).thenReturn("mocked-jwt-token");

        // Act
        String token = authenticatorUseCase.login(email, password);

        // Assert
        assertNotNull(token, "The token should not be null");
        assertEquals("mocked-jwt-token", token, "The token should match the mocked token");
    }

    @Test
    void validateToken_WhenValidToken_ShouldReturnValidation() {
        // Arrange
        String token = "valid-token";
        Validation validation = new Validation("testUser", "USER", true);

        when(authenticationPort.validateToken(token)).thenReturn(validation);

        // Act
        Validation result = authenticatorUseCase.validateToken(token);

        // Assert
        assertNotNull(result, "The validation result should not be null");
        assertEquals("testUser", result.getUsername(), "The username should match");
        assertEquals("USER", result.getRole(), "The role should match");
        assertEquals(true, result.getAuthorized(), "The authorization status should match");
    }

    @Test
    void validateToken_WhenInvalidToken_ShouldReturnInvalidValidation() {
        // Arrange
        String token = "invalid-token";
        Validation validation = new Validation("testUser", "USER", false);

        when(authenticationPort.validateToken(token)).thenReturn(validation);

        // Act
        Validation result = authenticatorUseCase.validateToken(token);

        // Assert
        assertNotNull(result, "The validation result should not be null");
        assertEquals("testUser", result.getUsername(), "The username should match");
        assertEquals("USER", result.getRole(), "The role should match");
        assertEquals(false, result.getAuthorized(), "The authorization status should match");
    }
}