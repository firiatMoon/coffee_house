package com.copybara.coffee_house.security;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Component
public class HashPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
            MessageDigest messageDigest = DigestUtils.getSha3_512Digest();
            byte[] hashedBytes  = messageDigest.digest(rawPassword.toString().getBytes(StandardCharsets.UTF_8));
        return Hex.encodeHexString(hashedBytes);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }
}
