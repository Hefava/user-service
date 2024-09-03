package com.bootcamp.usuario_service.domain.api.usecase;

import com.bootcamp.usuario_service.domain.model.Usuario;
import com.bootcamp.usuario_service.domain.spi.IAuthenticationPort;
import com.bootcamp.usuario_service.domain.spi.IEncryptPasswordPort;
import com.bootcamp.usuario_service.domain.spi.IUsuarioPersistencePort;
import com.bootcamp.usuario_service.domain.api.IUsuarioServicePort;
import com.bootcamp.usuario_service.domain.exception.MultipleUserValidationExceptions;
import com.bootcamp.usuario_service.domain.utils.UserValidationMessages;
import com.bootcamp.usuario_service.domain.utils.UsuarioUtils;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class UsuarioUseCase implements IUsuarioServicePort {

    private final IUsuarioPersistencePort usuarioPersistencePort;
    private final IEncryptPasswordPort encryptPasswordPort;
    private final IAuthenticationPort authenticationPort;

    public UsuarioUseCase(IUsuarioPersistencePort usuarioPersistencePort, IEncryptPasswordPort encryptPasswordPort, IAuthenticationPort authenticationPort) {
        this.usuarioPersistencePort = usuarioPersistencePort;
        this.encryptPasswordPort = encryptPasswordPort;
        this.authenticationPort = authenticationPort;
    }

    @Override
    public void registrarAuxBodega(Usuario usuario) {
        List<String> errors = new ArrayList<>();

        if (usuarioPersistencePort.existsByDocumentoDeIdentidad(usuario.getDocumentoDeIdentidad())) {
            errors.add(UserValidationMessages.USER_ALREADY_EXISTS);
        }

        if (usuario.getCorreo() == null || !usuario.getCorreo().matches(UsuarioUtils.EMAIL_REGEX)) {
            errors.add(UserValidationMessages.INVALID_EMAIL_FORMAT);
        }
        if (usuario.getDocumentoDeIdentidad() == null || !usuario.getDocumentoDeIdentidad().matches(UsuarioUtils.DOCUMENTO_REGEX)) {
            errors.add(UserValidationMessages.INVALID_ID_DOCUMENT);
        }
        if (usuario.getCelular() == null || usuario.getCelular().length() > UsuarioUtils.MAX_CELULAR_LENGTH) {
            errors.add(UserValidationMessages.CELLULAR_LENGTH_EXCEEDED);
        }
        if (usuario.getFechaNacimiento() == null || Period.between(usuario.getFechaNacimiento(), LocalDate.now()).getYears() < UsuarioUtils.MIN_AGE) {
            errors.add(UserValidationMessages.USER_UNDERAGE);
        }

        if (!errors.isEmpty()) {
            throw new MultipleUserValidationExceptions(errors);
        }

        encriptarClave(usuario);
        establecerRolAuxBodega(usuario);
        usuarioPersistencePort.saveUsuario(usuario);
    }

    @Override
    public String login(String email, String password) {
        Usuario usuario = authenticationPort.authenticate(email, password);
        return authenticationPort.generateToken(usuario);
    }

    private void encriptarClave(Usuario usuario) {
        String encryptedPassword = encryptPasswordPort.encrypt(usuario.getClave());
        usuario.setClave(encryptedPassword);
    }

    private void establecerRolAuxBodega(Usuario usuario) {
        usuario.setRolID(UsuarioUtils.ROL_AUX_BODEGA_ID);
    }
}