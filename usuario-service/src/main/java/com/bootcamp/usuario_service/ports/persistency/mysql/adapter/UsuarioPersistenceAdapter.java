package com.bootcamp.usuario_service.ports.persistency.mysql.adapter;

import com.bootcamp.usuario_service.domain.model.Usuario;
import com.bootcamp.usuario_service.domain.spi.IUsuarioPersistencePort;
import com.bootcamp.usuario_service.ports.persistency.mysql.entity.RolEntity;
import com.bootcamp.usuario_service.ports.persistency.mysql.entity.UsuarioEntity;
import com.bootcamp.usuario_service.ports.persistency.mysql.mapper.UsuarioEntityMapper;
import com.bootcamp.usuario_service.ports.persistency.mysql.repository.UsuarioRepository;

public class UsuarioPersistenceAdapter implements IUsuarioPersistencePort {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioEntityMapper usuarioMapper;

    public UsuarioPersistenceAdapter(UsuarioRepository usuarioRepository, UsuarioEntityMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    @Override
    public void saveUsuario(Usuario usuario) {
        RolEntity rolEntity = new RolEntity();
        rolEntity.setRolID(usuario.getRolID());

        UsuarioEntity usuarioEntity = usuarioMapper.toEntity(usuario);
        usuarioEntity.setRol(rolEntity);

        usuarioRepository.save(usuarioEntity);
    }

    @Override
    public boolean existsByDocumentoDeIdentidad(String documentoDeIdentidad) {
        return usuarioRepository.existsByDocumentoDeIdentidad(documentoDeIdentidad);
    }
}
