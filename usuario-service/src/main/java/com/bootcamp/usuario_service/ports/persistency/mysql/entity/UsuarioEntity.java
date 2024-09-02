package com.bootcamp.usuario_service.ports.persistency.mysql.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "usuarios")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usuarioID;

    @Column(nullable = false, length = 40)
    private String nombre;

    @Column(nullable = false, length = 30)
    private String apellido;

    @Column(nullable = false, unique = true)
    private String documentoDeIdentidad;

    @Column(nullable = false)
    private String celular;

    @Column(nullable = false)
    private LocalDate fechaNacimiento;

    @Column(nullable = false, unique = true)
    private String correo;

    @Column(nullable = false)
    private String clave;

    @ManyToOne
    @JoinColumn(name = "rolID", nullable = false)
    private RolEntity rol;
}