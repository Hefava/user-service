package com.bootcamp.usuario_service.ports.persistency.mysql.adapter;

import com.bootcamp.usuario_service.domain.model.Usuario;
import com.bootcamp.usuario_service.domain.spi.IAuthenticationPort;
import com.bootcamp.usuario_service.ports.service.JwtService;
import com.bootcamp.usuario_service.ports.persistency.mysql.mapper.UsuarioEntityMapper;
import com.bootcamp.usuario_service.ports.persistency.mysql.repository.UsuarioRepository;
import com.bootcamp.usuario_service.ports.persistency.mysql.entity.UsuarioEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationAdapter implements IAuthenticationPort {

    private final UsuarioRepository usuarioRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioEntityMapper usuarioEntityMapper;
    private final JwtService jwtService;

    @Override
    public Usuario authenticate(String email, String password) {
        UsuarioEntity usuarioEntity = usuarioRepository.findByCorreo(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        if (!passwordEncoder.matches(password, usuarioEntity.getClave())) {
            throw new IllegalArgumentException("Clave incorrecta");
        }

        return usuarioEntityMapper.toUsuario(usuarioEntity);
    }

    @Override
    public String generateToken(Usuario usuario) {
        return jwtService.getToken(usuarioEntityMapper.toEntity(usuario));
    }
}