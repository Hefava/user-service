package com.bootcamp.usuario_service.ports.application.http.mapper;

import com.bootcamp.usuario_service.domain.utils.Validation;
import com.bootcamp.usuario_service.ports.application.http.dto.ValidationResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ValidationResponseMapper {
    ValidationResponse toDto(Validation validation);
}