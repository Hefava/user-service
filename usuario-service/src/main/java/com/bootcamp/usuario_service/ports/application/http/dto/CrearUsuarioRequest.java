package com.bootcamp.usuario_service.ports.application.http.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CrearUsuarioRequest {

    private String nombre;

    private String apellido;

    private String correo;

    private String documentoDeIdentidad;

    private String celular;

    private String fechaNacimiento;

    private String clave;

    private Long rolID;
}