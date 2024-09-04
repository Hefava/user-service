package com.bootcamp.usuario_service.domain.api;

public interface IAthenticatorServicePort {
    String login(String email, String password);
}
