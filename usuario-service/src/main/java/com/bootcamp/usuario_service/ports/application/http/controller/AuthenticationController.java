package com.bootcamp.usuario_service.ports.application.http.controller;

import com.bootcamp.usuario_service.domain.api.IAthenticatorServicePort;
import com.bootcamp.usuario_service.domain.utils.UserValidationMessages;
import com.bootcamp.usuario_service.ports.application.http.dto.LoginRequest;
import com.bootcamp.usuario_service.ports.application.http.dto.LoginResponse;
import com.bootcamp.usuario_service.ports.application.http.dto.ValidationResponse;
import com.bootcamp.usuario_service.ports.application.http.mapper.ValidationResponseMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final IAthenticatorServicePort athenticatorServicePort;
    private final ValidationResponseMapper validationResponseMapper;

    @Operation(summary = UserValidationMessages.AUTH_SUMMARY_INICIAR_SESION, description = UserValidationMessages.AUTH_DESCRIPTION_INICIAR_SESION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = UserValidationMessages.INICIO_SESION_EXITOSO, content = @Content(schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "401", description = UserValidationMessages.CREDENCIALES_INCORRECTAS, content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Parameter(required = true) LoginRequest loginRequest) {
        String token = athenticatorServicePort.login(loginRequest.getCorreo(), loginRequest.getClave());
        LoginResponse response = new LoginResponse(token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/validate-token/{token}")
    public ResponseEntity<ValidationResponse> validateToken(@PathVariable @Parameter(required = true) String token) {
        ValidationResponse validationResponse = validationResponseMapper.toDto(athenticatorServicePort.validateToken(token));
        return ResponseEntity.ok(validationResponse);
    }
}