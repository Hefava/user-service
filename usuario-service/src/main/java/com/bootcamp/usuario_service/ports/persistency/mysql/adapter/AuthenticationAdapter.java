package com.bootcamp.usuario_service.ports.persistency.mysql.adapter;

import com.bootcamp.usuario_service.domain.exception.InvalidCredentialsException;
import com.bootcamp.usuario_service.domain.model.Usuario;
import com.bootcamp.usuario_service.domain.spi.IAuthenticationPort;
import com.bootcamp.usuario_service.domain.utils.Validation;
import com.bootcamp.usuario_service.ports.utils.JwtService;
import com.bootcamp.usuario_service.ports.persistency.mysql.mapper.UsuarioEntityMapper;
import com.bootcamp.usuario_service.ports.persistency.mysql.repository.UsuarioRepository;
import com.bootcamp.usuario_service.ports.persistency.mysql.entity.UsuarioEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import static com.bootcamp.usuario_service.domain.utils.UserValidationMessages.CREDENCIALES_INCORRECTAS;
import static com.bootcamp.usuario_service.domain.utils.UserValidationMessages.USUARIO_NO_ENCONTRADO;

@Component
@RequiredArgsConstructor
public class AuthenticationAdapter implements IAuthenticationPort {

    private final UsuarioRepository usuarioRepository;
    private final AuthenticationManager authenticationManager;
    private final UsuarioEntityMapper usuarioEntityMapper;
    private final JwtService jwtService;

    @Override
    public Usuario authenticate(String email, String password) {
        UsuarioEntity usuarioEntity = usuarioRepository.findByCorreo(email)
                .orElseThrow(() -> new InvalidCredentialsException(CREDENCIALES_INCORRECTAS));

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));

            return usuarioEntityMapper.toUsuario(usuarioEntity);

        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException(CREDENCIALES_INCORRECTAS, e);
        }
    }

    @Override
    public String generateToken(Usuario usuario) {
        return jwtService.getToken(usuarioEntityMapper.toEntity(usuario));
    }

    @Override
    public Validation validateToken(String token) {
        String correo = jwtService.extractUsername(token);
        UsuarioEntity usuarioEntity = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException(USUARIO_NO_ENCONTRADO));
        String rol = usuarioEntity.getRol().getNombre().name();
        Boolean valid = jwtService.isTokenValid(token, usuarioEntity);
        return new Validation(correo, rol, valid);
    }
}