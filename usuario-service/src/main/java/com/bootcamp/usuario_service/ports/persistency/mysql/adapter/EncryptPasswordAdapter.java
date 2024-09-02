package com.bootcamp.usuario_service.ports.persistency.mysql.adapter;

import com.bootcamp.usuario_service.domain.spi.IEncryptPasswordPort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class EncryptPasswordAdapter implements IEncryptPasswordPort {

    private final BCryptPasswordEncoder encoder;

    public EncryptPasswordAdapter() {
        this.encoder = new BCryptPasswordEncoder();
    }

    @Override
    public String encrypt(String password) {
        return encoder.encode(password);
    }
}
