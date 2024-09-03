package com.bootcamp.usuario_service.ports.application.http.controller;

import com.bootcamp.usuario_service.domain.api.IUsuarioServicePort;
import com.bootcamp.usuario_service.domain.model.Usuario;
import com.bootcamp.usuario_service.ports.application.http.dto.CrearUsuarioRequest;
import com.bootcamp.usuario_service.ports.application.http.dto.LoginRequest;
import com.bootcamp.usuario_service.ports.application.http.dto.LoginResponse;
import com.bootcamp.usuario_service.ports.application.http.mapper.CrearUsuarioRequestMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final IUsuarioServicePort usuarioServicePort;
    private final CrearUsuarioRequestMapper requestMapper;

    @Operation(summary = "Registrar un nuevo usuario", description = "Crea un nuevo usuario auxiliar de bodega en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta, por ejemplo, datos de usuario inválidos", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/registrar")
    public ResponseEntity<Void> registrarAuxBodega(
            @RequestBody @Parameter(description = "Datos del usuario a crear", required = true) CrearUsuarioRequest request) {
        Usuario usuario = requestMapper.toDomain(request);
        usuarioServicePort.registrarAuxBodega(usuario);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Iniciar sesión", description = "Autentica a un usuario en el sistema y retorna un token JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso", content = @Content(schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "401", description = "Credenciales incorrectas", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Parameter(description = "Datos de inicio de sesión", required = true) LoginRequest loginRequest) {
        String token = usuarioServicePort.login(loginRequest.getCorreo(), loginRequest.getClave());
        LoginResponse response = new LoginResponse(token);
        return ResponseEntity.ok(response);
    }
}
