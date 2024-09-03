package com.bootcamp.usuario_service.infrastructure.configuration;

import com.bootcamp.usuario_service.domain.api.IUsuarioServicePort;
import com.bootcamp.usuario_service.domain.api.usecase.UsuarioUseCase;
import com.bootcamp.usuario_service.domain.spi.IEncryptPasswordPort;
import com.bootcamp.usuario_service.domain.spi.IUsuarioPersistencePort;
import com.bootcamp.usuario_service.domain.spi.IAuthenticationPort;
import com.bootcamp.usuario_service.ports.service.JwtService;
import com.bootcamp.usuario_service.ports.persistency.mysql.repository.UsuarioRepository;
import com.bootcamp.usuario_service.ports.persistency.mysql.adapter.UsuarioPersistenceAdapter;
import com.bootcamp.usuario_service.ports.persistency.mysql.adapter.AuthenticationAdapter;
import com.bootcamp.usuario_service.ports.persistency.mysql.mapper.UsuarioEntityMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
public class BeanConfiguration {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public BeanConfiguration(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Bean
    public IUsuarioServicePort usuarioServicePort(
            IUsuarioPersistencePort usuarioPersistencePort,
            IEncryptPasswordPort encryptPasswordPort,
            IAuthenticationPort authenticationPort) {
        return new UsuarioUseCase(usuarioPersistencePort, encryptPasswordPort, authenticationPort);
    }

    @Bean
    public IUsuarioPersistencePort usuarioPersistencePort(
            UsuarioRepository usuarioRepository,
            UsuarioEntityMapper usuarioMapper) {
        return new UsuarioPersistenceAdapter(usuarioRepository, usuarioMapper);
    }

    @Bean
    public IAuthenticationPort authenticationPort(
            UsuarioRepository usuarioRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            UsuarioEntityMapper usuarioEntityMapper,
            JwtService jwtService) {
        return new AuthenticationAdapter(usuarioRepository, authenticationManager, passwordEncoder, usuarioEntityMapper, jwtService);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> usuarioRepository.findByCorreo(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    }
}