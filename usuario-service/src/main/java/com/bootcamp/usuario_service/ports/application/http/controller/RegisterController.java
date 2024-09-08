package com.bootcamp.usuario_service.ports.application.http.controller;

import com.bootcamp.usuario_service.domain.api.IRegistrarServicePort;
import com.bootcamp.usuario_service.domain.model.Usuario;
import com.bootcamp.usuario_service.domain.utils.UserValidationMessages;
import com.bootcamp.usuario_service.ports.application.http.dto.CrearUsuarioRequest;
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
@RequestMapping("/registrar")
@RequiredArgsConstructor
public class RegisterController {

    private final IRegistrarServicePort usuarioServicePort;
    private final CrearUsuarioRequestMapper requestMapper;

    @Operation(summary = UserValidationMessages.REGISTER_SUMMARY_REGISTRAR_USUARIO, description = UserValidationMessages.REGISTER_DESCRIPTION_REGISTRAR_USUARIO)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = UserValidationMessages.USUARIO_CREADO_EXITO),
            @ApiResponse(responseCode = "400", description = UserValidationMessages.SOLICITUD_INCORRECTA, content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = UserValidationMessages.ERROR_INTERNO_SERVIDOR, content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/registrar-auxbodega")
    public ResponseEntity<Void> registrarAuxBodega(
            @RequestBody @Parameter(required = true) CrearUsuarioRequest request) {
        Usuario usuario = requestMapper.toDomain(request);
        usuarioServicePort.registrarAuxBodega(usuario);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
