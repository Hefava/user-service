package com.bootcamp.usuario_service.domain.api.usecase;

import com.bootcamp.usuario_service.domain.model.Usuario;
import com.bootcamp.usuario_service.domain.spi.IEncryptPasswordPort;
import com.bootcamp.usuario_service.domain.spi.IUsuarioPersistencePort;
import com.bootcamp.usuario_service.domain.api.IRegistrarServicePort;
import com.bootcamp.usuario_service.domain.exception.MultipleUserValidationExceptions;
import com.bootcamp.usuario_service.domain.utils.UserValidationMessages;
import com.bootcamp.usuario_service.domain.utils.UsuarioUtils;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class RegistrarUseCase implements IRegistrarServicePort {

    private final IUsuarioPersistencePort usuarioPersistencePort;
    private final IEncryptPasswordPort encryptPasswordPort;

    public RegistrarUseCase(IUsuarioPersistencePort usuarioPersistencePort, IEncryptPasswordPort encryptPasswordPort) {
        this.usuarioPersistencePort = usuarioPersistencePort;
        this.encryptPasswordPort = encryptPasswordPort;
    }

    @Override
    public void registrarAuxBodega(Usuario usuario) {
        List<String> errors = new ArrayList<>();

        if (usuarioPersistencePort.existsByCorreo(usuario.getCorreo())) {
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

    private void encriptarClave(Usuario usuario) {
        String encryptedPassword = encryptPasswordPort.encrypt(usuario.getClave());
        usuario.setClave(encryptedPassword);
    }

    private void establecerRolAuxBodega(Usuario usuario) {
        usuario.setRolID(UsuarioUtils.ROL_AUX_BODEGA_ID);
    }
}