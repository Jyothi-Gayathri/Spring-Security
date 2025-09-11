package com.project.security;

import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        try {
            return AESUtil.encrypt(rawPassword.toString()); // Encrypt before storing
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting password", e);
        }
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        try {
            String decryptedPassword = AESUtil.decrypt(encodedPassword); // Decrypt for comparison
            return rawPassword.toString().equals(decryptedPassword);
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting password", e);
        }
    }
}