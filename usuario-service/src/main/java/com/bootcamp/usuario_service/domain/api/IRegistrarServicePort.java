package com.bootcamp.usuario_service.domain.api;

import com.bootcamp.usuario_service.domain.model.Usuario;

public interface IRegistrarServicePort {
    void registrarAuxBodega(Usuario usuario);
    void registrarCliente(Usuario usuario);
}
