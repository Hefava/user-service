package com.bootcamp.usuario_service.domain.api;

import com.bootcamp.usuario_service.domain.model.Usuario;

public interface IUsuarioServicePort {
    void registrarAuxBodega(Usuario usuario);
    String login(String email, String password);
}
