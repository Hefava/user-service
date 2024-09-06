package com.bootcamp.usuario_service.domain.api;

import com.bootcamp.usuario_service.domain.utils.Validation;

public interface IAthenticatorServicePort {
    String login(String email, String password);
    Validation validateToken(String token);
}
