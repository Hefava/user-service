package com.bootcamp.usuario_service.ports.application.http.mapper;

import com.bootcamp.usuario_service.domain.model.Usuario;
import com.bootcamp.usuario_service.ports.application.http.dto.LoginRequest;
import com.bootcamp.usuario_service.ports.persistency.mysql.entity.UsuarioEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginRequestMapper {
    Usuario toDomain(LoginRequest request);

    UsuarioEntity toEntity(LoginRequest request);
}