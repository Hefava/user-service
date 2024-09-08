package com.bootcamp.usuario_service.ports.persistency.mysql.entity;

import com.bootcamp.usuario_service.domain.utils.RolEnum;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "rol")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RolEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rolID;

    @Column(nullable = false, unique = true)
    private RolEnum nombre;

    @Column(nullable = false)
    private String descripcion;
}