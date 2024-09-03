package com.bootcamp.usuario_service.ports.persistency.mysql.repository;

import com.bootcamp.usuario_service.ports.persistency.mysql.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    boolean existsByDocumentoDeIdentidad(String documentoDeIdentidad);

    Optional<UsuarioEntity> findByCorreo(String correo);
}