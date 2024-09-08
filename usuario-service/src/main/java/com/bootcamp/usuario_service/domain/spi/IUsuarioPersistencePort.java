package com.bootcamp.usuario_service.domain.spi;

import com.bootcamp.usuario_service.domain.model.Usuario;

public interface IUsuarioPersistencePort {
    void saveUsuario(Usuario usuario);
    boolean existsByCorreo(String correo);
}
