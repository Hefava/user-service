package com.bootcamp.usuario_service.domain.model;

import com.bootcamp.usuario_service.domain.utils.RolEnum;

public class Rol {
    private Long rolID;
    private RolEnum nombre;
    private String descripcion;

    public Rol() {
    }

    public Rol(Long rolID, RolEnum nombre, String descripcion) {
        this.rolID = rolID;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Long getRolID() {
        return rolID;
    }

    public void setRolID(Long rolID) {
        this.rolID = rolID;
    }

    public RolEnum getNombre() {
        return nombre;
    }

    public void setNombre(RolEnum nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
