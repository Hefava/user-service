package com.bootcamp.usuario_service.domain.spi;

import com.bootcamp.usuario_service.domain.model.Usuario;

public interface IAuthenticationPort {
    Usuario authenticate(String email, String password);
    String generateToken(Usuario usuario);
}
