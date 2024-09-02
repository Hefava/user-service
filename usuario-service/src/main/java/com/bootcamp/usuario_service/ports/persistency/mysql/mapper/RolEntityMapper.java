package com.bootcamp.usuario_service.ports.persistency.mysql.mapper;

import com.bootcamp.usuario_service.domain.model.Rol;
import com.bootcamp.usuario_service.ports.persistency.mysql.entity.RolEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RolEntityMapper {

    @Mapping(target = "rolID", source = "rolID")
    RolEntity toEntity(Rol rol);

    Rol toRol(RolEntity rolEntity);

    List<Rol> toRolesList(List<RolEntity> rolEntities);

    default RolEntity map(Long rolID) {
        if (rolID == null) {
            return null;
        }
        RolEntity rolEntity = new RolEntity();
        rolEntity.setRolID(rolID);
        return rolEntity;
    }
}