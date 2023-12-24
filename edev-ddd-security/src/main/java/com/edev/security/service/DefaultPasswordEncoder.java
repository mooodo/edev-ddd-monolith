package com.edev.security.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

public class DefaultPasswordEncoder extends DelegatingPasswordEncoder {
    @Value("edev.security.passwordEncoder")
    private static final String passwordEncoder = "bcrypt";

    public static DefaultPasswordEncoder build() {
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());
        encoders.put("sha256", new StandardPasswordEncoder());
        return new DefaultPasswordEncoder(passwordEncoder, encoders);
    }

    protected DefaultPasswordEncoder(String idForEncode, Map<String, PasswordEncoder> idToPasswordEncoder) {
        super(idForEncode, idToPasswordEncoder);
    }
}
