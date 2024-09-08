package com.bootcamp.usuario_service.Usuario.ports.application.http.controller;

import com.bootcamp.usuario_service.domain.api.IRegistrarServicePort;
import com.bootcamp.usuario_service.domain.model.Usuario;
import com.bootcamp.usuario_service.ports.application.http.dto.CrearUsuarioRequest;
import com.bootcamp.usuario_service.ports.application.http.mapper.CrearUsuarioRequestMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IRegistrarServicePort registrarServicePort;

    @MockBean
    private CrearUsuarioRequestMapper requestMapper;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void testRegistrarAuxBodega_Success() throws Exception {
        // Arrange
        CrearUsuarioRequest request = new CrearUsuarioRequest();
        request.setCorreo("test@correo.com");
        request.setClave("password123");
        request.setNombre("Test User");

        Usuario usuario = new Usuario();
        usuario.setCorreo("test@correo.com");
        usuario.setClave("password123");
        usuario.setNombre("Test User");

        // Simulamos el mapeo del DTO a dominio y la llamada al servicio
        Mockito.when(requestMapper.toDomain(any(CrearUsuarioRequest.class))).thenReturn(usuario);
        doNothing().when(registrarServicePort).registrarAuxBodega(any(Usuario.class));

        // Act & Assert: Ejecutamos la solicitud HTTP POST y verificamos que responde con el estado 201 (CREATED)
        mockMvc.perform(post("/registrar/registrar-auxbodega")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"correo\": \"test@correo.com\", \"clave\": \"password123\", \"nombre\": \"Test User\" }"))
                .andExpect(status().isCreated());
    }

    @Test
    void testRegistrarAuxBodega_InternalServerError() throws Exception {
        CrearUsuarioRequest request = new CrearUsuarioRequest();
        request.setCorreo("test@correo.com");
        request.setClave("password123");
        request.setNombre("Test User");

        Usuario usuario = new Usuario();
        usuario.setCorreo("test@correo.com");
        usuario.setClave("password123");
        usuario.setNombre("Test User");

        Mockito.when(requestMapper.toDomain(any(CrearUsuarioRequest.class))).thenReturn(usuario);
        Mockito.doThrow(new RuntimeException("Error interno")).when(registrarServicePort).registrarAuxBodega(any(Usuario.class));

        // Act & Assert: Verificamos que se devuelve el estado 500 (INTERNAL_SERVER_ERROR)
        mockMvc.perform(post("/registrar/registrar-auxbodega")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"correo\": \"test@correo.com\", \"clave\": \"password123\", \"nombre\": \"Test User\" }"))
                .andExpect(status().isInternalServerError());
    }
}