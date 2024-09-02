package com.bootcamp.usuario_service.infrastructure.configuration;

import com.bootcamp.usuario_service.domain.api.IUsuarioServicePort;
import com.bootcamp.usuario_service.domain.api.usecase.RegistrarAuxBodegaUseCase;
import com.bootcamp.usuario_service.domain.spi.IEncryptPasswordPort;
import com.bootcamp.usuario_service.domain.spi.IUsuarioPersistencePort;
import com.bootcamp.usuario_service.ports.persistency.mysql.adapter.UsuarioPersistenceAdapter;
import com.bootcamp.usuario_service.ports.persistency.mysql.mapper.UsuarioEntityMapper;
import com.bootcamp.usuario_service.ports.persistency.mysql.repository.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public IUsuarioServicePort registrarUsuarioUseCase(IUsuarioPersistencePort usuarioPersistencePort, IEncryptPasswordPort encryptPasswordPort) {
        return new RegistrarAuxBodegaUseCase(usuarioPersistencePort, encryptPasswordPort);
    }

    @Bean
    public IUsuarioPersistencePort usuarioPersistencePort(UsuarioRepository usuarioRepository, UsuarioEntityMapper usuarioMapper) {
        return new UsuarioPersistenceAdapter(usuarioRepository, usuarioMapper);
    }
}
