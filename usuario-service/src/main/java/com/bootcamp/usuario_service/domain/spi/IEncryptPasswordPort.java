package com.bootcamp.usuario_service.domain.spi;

public interface IEncryptPasswordPort {
    String encrypt(String password);
}