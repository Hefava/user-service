package com.bootcamp.usuario_service.domain.spi;

import com.bootcamp.usuario_service.domain.model.Usuario;
import com.bootcamp.usuario_service.domain.utils.Validation;

public interface IAuthenticationPort {
    Usuario authenticate(String email, String password);
    String generateToken(Usuario usuario);
    Validation validateToken(String token);
}
