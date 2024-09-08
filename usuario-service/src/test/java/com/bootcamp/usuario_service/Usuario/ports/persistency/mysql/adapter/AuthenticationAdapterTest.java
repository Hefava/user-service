package com.bootcamp.usuario_service.Usuario.ports.persistency.mysql.adapter;

import com.bootcamp.usuario_service.domain.exception.InvalidCredentialsException;
import com.bootcamp.usuario_service.domain.model.Usuario;
import com.bootcamp.usuario_service.domain.utils.RolEnum;
import com.bootcamp.usuario_service.domain.utils.Validation;
import com.bootcamp.usuario_service.ports.persistency.mysql.adapter.AuthenticationAdapter;
import com.bootcamp.usuario_service.ports.persistency.mysql.entity.RolEntity;
import com.bootcamp.usuario_service.ports.persistency.mysql.entity.UsuarioEntity;
import com.bootcamp.usuario_service.ports.persistency.mysql.mapper.UsuarioEntityMapper;
import com.bootcamp.usuario_service.ports.persistency.mysql.repository.UsuarioRepository;
import com.bootcamp.usuario_service.ports.utils.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static com.bootcamp.usuario_service.domain.utils.UserValidationMessages.CREDENCIALES_INCORRECTAS;
import static com.bootcamp.usuario_service.domain.utils.UserValidationMessages.USUARIO_NO_ENCONTRADO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class AuthenticationAdapterTest {

    private UsuarioRepository usuarioRepository;
    private AuthenticationManager authenticationManager;
    private UsuarioEntityMapper usuarioEntityMapper;
    private JwtService jwtService;
    private AuthenticationAdapter authenticationAdapter;

    @BeforeEach
    void setUp() {
        usuarioRepository = Mockito.mock(UsuarioRepository.class);
        authenticationManager = Mockito.mock(AuthenticationManager.class);
        usuarioEntityMapper = Mockito.mock(UsuarioEntityMapper.class);
        jwtService = Mockito.mock(JwtService.class);

        authenticationAdapter = new AuthenticationAdapter(
                usuarioRepository,
                authenticationManager,
                usuarioEntityMapper,
                jwtService
        );
    }

    @Test
    void testAuthenticate_Success() {
        // Arrange
        String email = "test@correo.com";
        String password = "password123";
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setCorreo(email);

        Mockito.when(usuarioRepository.findByCorreo(email)).thenReturn(Optional.of(usuarioEntity));
        Mockito.when(usuarioEntityMapper.toUsuario(any(UsuarioEntity.class))).thenReturn(new Usuario());

        // Act
        Usuario result = authenticationAdapter.authenticate(email, password);

        // Assert
        assertNotNull(result);
        Mockito.verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void testAuthenticate_UserNotFound() {
        // Arrange
        String email = "test@correo.com";
        String password = "password123";

        Mockito.when(usuarioRepository.findByCorreo(email)).thenReturn(Optional.empty());

        // Act & Assert
        InvalidCredentialsException exception = assertThrows(InvalidCredentialsException.class, () -> {
            authenticationAdapter.authenticate(email, password);
        });

        assertEquals(CREDENCIALES_INCORRECTAS, exception.getMessage());
    }

    @Test
    void testAuthenticate_InvalidCredentials() {
        // Arrange
        String email = "test@correo.com";
        String password = "password123";
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setCorreo(email);

        Mockito.when(usuarioRepository.findByCorreo(email)).thenReturn(Optional.of(usuarioEntity));
        Mockito.when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        // Act & Assert
        InvalidCredentialsException exception = assertThrows(InvalidCredentialsException.class, () -> {
            authenticationAdapter.authenticate(email, password);
        });

        assertEquals(CREDENCIALES_INCORRECTAS, exception.getMessage());
    }

    @Test
    void testGenerateToken_Success() {
        // Arrange
        Usuario usuario = new Usuario();
        String expectedToken = "jwt_token";

        Mockito.when(jwtService.getToken(any(UsuarioEntity.class))).thenReturn(expectedToken);
        Mockito.when(usuarioEntityMapper.toEntity(any(Usuario.class))).thenReturn(new UsuarioEntity());

        // Act
        String token = authenticationAdapter.generateToken(usuario);

        // Assert
        assertEquals(expectedToken, token);
    }

    @Test
    void testValidateToken_Success() {
        // Arrange
        String token = "valid_token";
        String email = "test@correo.com";
        String role = "ADMIN";  // Debe coincidir con el valor en el enum RolEnum

        // Creamos un objeto RolEntity y le asignamos el valor del enum RolEnum.ADMIN
        RolEntity rolEntity = new RolEntity();
        rolEntity.setNombre(RolEnum.ADMIN);  // Asignamos el valor del enum ADMIN

        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setCorreo(email);
        usuarioEntity.setRol(rolEntity);  // Asignamos el rol al usuario

        // Mock the JwtService and UsuarioRepository responses
        Mockito.when(jwtService.extractUsername(token)).thenReturn(email);
        Mockito.when(usuarioRepository.findByCorreo(email)).thenReturn(Optional.of(usuarioEntity));
        Mockito.when(jwtService.isTokenValid(token, usuarioEntity)).thenReturn(true);

        // Act
        Validation validation = authenticationAdapter.validateToken(token);

        // Assert
        assertNotNull(validation);
        assertEquals(email, validation.getUsername());
        assertEquals(role, validation.getRole());
        assertTrue(validation.getAuthorized());
    }

    @Test
    void testValidateToken_UserNotFound() {
        // Arrange
        String token = "valid_token";
        String email = "test@correo.com";

        Mockito.when(jwtService.extractUsername(token)).thenReturn(email);
        Mockito.when(usuarioRepository.findByCorreo(email)).thenReturn(Optional.empty());

        // Act & Assert
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
            authenticationAdapter.validateToken(token);
        });

        assertEquals(USUARIO_NO_ENCONTRADO, exception.getMessage());
    }
}