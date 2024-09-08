package com.bootcamp.usuario_service.domain.api.usecase;

import com.bootcamp.usuario_service.domain.api.IAthenticatorServicePort;
import com.bootcamp.usuario_service.domain.model.Usuario;
import com.bootcamp.usuario_service.domain.spi.IAuthenticationPort;
import com.bootcamp.usuario_service.domain.utils.Validation;

public class AuthenticatorUseCase implements IAthenticatorServicePort {
    private final IAuthenticationPort authenticationPort;

    public AuthenticatorUseCase(IAuthenticationPort authenticationPort) {
        this.authenticationPort = authenticationPort;
    }

    @Override
    public String login(String email, String password) {
        Usuario usuario = authenticationPort.authenticate(email, password);
        return authenticationPort.generateToken(usuario);
    }

    @Override
    public Validation validateToken(String token) {
        return authenticationPort.validateToken(token);
    }
}