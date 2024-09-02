package com.bootcamp.usuario_service.UsuarioAuxiliarBodega.ports.persistency.mysql.adapter;

import com.bootcamp.usuario_service.domain.model.Usuario;
import com.bootcamp.usuario_service.ports.persistency.mysql.adapter.UsuarioPersistenceAdapter;
import com.bootcamp.usuario_service.ports.persistency.mysql.entity.RolEntity;
import com.bootcamp.usuario_service.ports.persistency.mysql.entity.UsuarioEntity;
import com.bootcamp.usuario_service.ports.persistency.mysql.mapper.UsuarioEntityMapper;
import com.bootcamp.usuario_service.ports.persistency.mysql.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class UsuarioPersistenceAdapterTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioEntityMapper usuarioMapper;

    @InjectMocks
    private UsuarioPersistenceAdapter usuarioPersistenceAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveUsuario_ShouldSaveUsuarioCorrectly() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setRolID(1L);

        UsuarioEntity usuarioEntity = new UsuarioEntity();
        RolEntity rolEntity = new RolEntity();
        rolEntity.setRolID(1L);
        usuarioEntity.setRol(rolEntity);

        when(usuarioMapper.toEntity(usuario)).thenReturn(usuarioEntity);

        // Act
        usuarioPersistenceAdapter.saveUsuario(usuario);

        // Assert
        verify(usuarioMapper).toEntity(usuario);
        verify(usuarioRepository).save(usuarioEntity);
    }

    @Test
    void existsByDocumentoDeIdentidad_ShouldReturnTrueIfExists() {
        // Arrange
        String documentoDeIdentidad = "12345678";
        when(usuarioRepository.existsByDocumentoDeIdentidad(documentoDeIdentidad)).thenReturn(true);

        // Act
        boolean exists = usuarioPersistenceAdapter.existsByDocumentoDeIdentidad(documentoDeIdentidad);

        // Assert
        assertTrue(exists);
        verify(usuarioRepository).existsByDocumentoDeIdentidad(documentoDeIdentidad);
    }

    @Test
    void existsByDocumentoDeIdentidad_ShouldReturnFalseIfNotExists() {
        // Arrange
        String documentoDeIdentidad = "12345678";
        when(usuarioRepository.existsByDocumentoDeIdentidad(documentoDeIdentidad)).thenReturn(false);

        // Act
        boolean exists = usuarioPersistenceAdapter.existsByDocumentoDeIdentidad(documentoDeIdentidad);

        // Assert
        assertFalse(exists);
        verify(usuarioRepository).existsByDocumentoDeIdentidad(documentoDeIdentidad);
    }
}
