package com.bootcamp.usuario_service.domain.model;

public class Rol {
    private Long rolID;
    private String nombre;
    private String descripcion;

    public Rol() {
    }

    public Rol(Long rolID, String nombre, String descripcion) {
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
