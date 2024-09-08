package com.bootcamp.usuario_service.domain.utils;

public class Validation {
    private String username;
    private String role;
    private Boolean authorized;

    public Validation() {
    }

    public Validation(String username, String role, Boolean authorized) {
        this.username = username;
        this.role = role;
        this.authorized = authorized;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getAuthorized() {
        return authorized;
    }

    public void setAuthorized(Boolean authorized) {
        this.authorized = authorized;
    }
}
