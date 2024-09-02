package com.bootcamp.usuario_service.ports.application.http.mapper;

import com.bootcamp.usuario_service.domain.model.Usuario;
import com.bootcamp.usuario_service.ports.application.http.dto.CrearUsuarioRequest;
import com.bootcamp.usuario_service.ports.persistency.mysql.entity.UsuarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CrearUsuarioRequestMapper {
    @Mapping(target = "rolID", ignore = true)
    Usuario toDomain(CrearUsuarioRequest request);

    @Mapping(target = "rol", ignore = true)
    UsuarioEntity toEntity(CrearUsuarioRequest request);
}