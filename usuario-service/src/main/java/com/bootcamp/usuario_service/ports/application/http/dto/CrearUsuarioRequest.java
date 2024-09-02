package com.bootcamp.usuario_service.ports.application.http.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CrearUsuarioRequest {

    @Schema(description = "Nombre del usuario", example = "Juan Pérez")
    private String nombre;

    @Schema(description = "Apellido del usuario", example = "Pérez")
    private String apellido;

    @Schema(description = "Correo electrónico del usuario", example = "juan.perez@example.com")
    private String correo;

    @Schema(description = "Número de documento de identidad del usuario", example = "12345678")
    private String documentoDeIdentidad;

    @Schema(description = "Número de celular del usuario", example = "+1234567890")
    private String celular;

    @Schema(description = "Fecha de nacimiento del usuario", example = "2000-01-01")
    private String fechaNacimiento;

    @Schema(description = "Clave del usuario", example = "password123")
    private String clave;

    @Schema(description = "ID del rol del usuario", example = "1")
    private Long rolID;
}