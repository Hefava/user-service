package com.bootcamp.usuario_service.Usuario.ports.application.http.controller;

import com.bootcamp.usuario_service.domain.api.IAthenticatorServicePort;
import com.bootcamp.usuario_service.domain.utils.UserValidationMessages;
import com.bootcamp.usuario_service.domain.utils.Validation;
import com.bootcamp.usuario_service.ports.application.http.dto.LoginRequest;
import com.bootcamp.usuario_service.ports.application.http.dto.ValidationResponse;
import com.bootcamp.usuario_service.ports.application.http.mapper.ValidationResponseMapper;
import com.bootcamp.usuario_service.ports.persistency.mysql.repository.UsuarioRepository;
import com.bootcamp.usuario_service.ports.utils.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private IAthenticatorServicePort authenticatorServicePort;

    @MockBean
    private ValidationResponseMapper validationResponseMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testUserNotFound() {
        // Arrange: Mock the repository to return empty for non-existing user
        when(usuarioRepository.findByCorreo(anyString())).thenReturn(Optional.empty());

        // Act & Assert: Expect an exception when trying to load non-existent user
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userDetailsService.loadUserByUsername("nonexistent@correo.com");
        });

        // Assert: Ensure the correct error message is thrown
        assertEquals(UserValidationMessages.USUARIO_NO_ENCONTRADO, exception.getMessage());
    }

    // Test for login endpoint
    @Test
    void testLoginSuccess() throws Exception {
        // Arrange: Prepare the login request and mock the response from the service
        LoginRequest loginRequest = new LoginRequest("test@correo.com", "password123");
        when(authenticatorServicePort.login(anyString(), anyString())).thenReturn("mocked-jwt-token");

        // Act and Assert: Perform the login and verify the response
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mocked-jwt-token"));
    }

    @Test
    void testValidateTokenSuccess() throws Exception {
        // Arrange: Mock a valid token validation
        String validToken = "valid-token";
        Validation mockValidation = new Validation("testUser", "USER", true);  // Crea un mock de Validation

        ValidationResponse mockValidationResponse = new ValidationResponse("testUser", "USER", true);  // Ajusta el DTO a la estructura correcta

        when(authenticatorServicePort.validateToken(validToken)).thenReturn(mockValidation);  // Cambia el retorno a Validation
        when(validationResponseMapper.toDto(Mockito.any(Validation.class))).thenReturn(mockValidationResponse);  // Cambia el mapeo para usar Validation

        // Act and Assert: Perform token validation and verify the response
        mockMvc.perform(post("/auth/validate-token/" + validToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testUser"))
                .andExpect(jsonPath("$.role").value("USER"))
                .andExpect(jsonPath("$.authorized").value(true));
    }

    @Test
    void testValidateTokenInvalid() throws Exception {
        // Arrange: Mock an invalid token validation
        String invalidToken = "invalid-token";
        Validation mockValidation = new Validation("testUser", "USER", false);  // Mock de Validation para token inválido

        ValidationResponse mockValidationResponse = new ValidationResponse("testUser", "USER", false);  // Ajusta el DTO para un token inválido

        when(authenticatorServicePort.validateToken(invalidToken)).thenReturn(mockValidation);  // Cambia el retorno a Validation
        when(validationResponseMapper.toDto(Mockito.any(Validation.class))).thenReturn(mockValidationResponse);  // Cambia el mapeo para usar Validation

        // Act and Assert: Perform token validation and expect failure
        mockMvc.perform(post("/auth/validate-token/" + invalidToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testUser"))
                .andExpect(jsonPath("$.role").value("USER"))
                .andExpect(jsonPath("$.authorized").value(false));
    }
}