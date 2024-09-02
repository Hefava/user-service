package com.bootcamp.usuario_service.ports.persistency.mysql.mapper;

import com.bootcamp.usuario_service.domain.model.Usuario;
import com.bootcamp.usuario_service.ports.persistency.mysql.entity.UsuarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = RolEntityMapper.class)
public interface UsuarioEntityMapper {

    @Mapping(target = "rol", source = "rolID")
    UsuarioEntity toEntity(Usuario usuario);

    Usuario toUsuario(UsuarioEntity usuarioEntity);

    List<Usuario> toUsuariosList(List<UsuarioEntity> usuarioEntities);
}
