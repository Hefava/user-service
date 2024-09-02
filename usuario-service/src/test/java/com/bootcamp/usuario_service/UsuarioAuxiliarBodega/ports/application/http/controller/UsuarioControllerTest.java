package com.bootcamp.usuario_service.UsuarioAuxiliarBodega.ports.application.http.controller;

import com.bootcamp.usuario_service.domain.api.IUsuarioServicePort;
import com.bootcamp.usuario_service.domain.model.Usuario;
import com.bootcamp.usuario_service.ports.application.http.controller.UsuarioController;
import com.bootcamp.usuario_service.ports.application.http.dto.CrearUsuarioRequest;
import com.bootcamp.usuario_service.ports.application.http.mapper.CrearUsuarioRequestMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;

class UsuarioControllerTest {

    @Mock
    private IUsuarioServicePort usuarioServicePort;

    @Mock
    private CrearUsuarioRequestMapper requestMapper;

    @InjectMocks
    private UsuarioController usuarioController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
    }

    @Test
    void registrarAuxBodega_WhenValidRequest_ShouldReturnCreatedStatus() throws Exception {
        CrearUsuarioRequest request = new CrearUsuarioRequest();
        request.setNombre("Juan");
        request.setApellido("Pérez");
        request.setDocumentoDeIdentidad("12345678");
        request.setCelular("+1234567890");
        request.setFechaNacimiento("2000-01-01");
        request.setCorreo("juan.perez@example.com");
        request.setClave("password123");
        request.setRolID(1L);

        Usuario usuario = new Usuario(
                null,
                "Juan",
                "Pérez",
                "12345678",
                "+1234567890",
                LocalDate.parse("2000-01-01"),
                "juan.perez@example.com",
                "password123",
                1L
        );

        when(requestMapper.toDomain(request)).thenReturn(usuario);
        doNothing().when(usuarioServicePort).registrarAuxBodega(usuario);

        mockMvc.perform(post("/usuario/registrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isCreated());
    }
}