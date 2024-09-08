package com.bootcamp.usuario_service.Usuario.ports.persistency.mysql.adapter;

import com.bootcamp.usuario_service.ports.persistency.mysql.adapter.EncryptPasswordAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;

class EncryptPasswordAdapterTest {

    private EncryptPasswordAdapter encryptPasswordAdapter;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    void setUp() {
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
        encryptPasswordAdapter = new EncryptPasswordAdapter();
    }

    @Test
    void encrypt_ShouldReturnEncryptedPassword() {
        // Arrange
        String rawPassword = "mySecretPassword";

        // Act
        String result = encryptPasswordAdapter.encrypt(rawPassword);

        // Assert
        assertTrue(bCryptPasswordEncoder.matches(rawPassword, result));
    }
}