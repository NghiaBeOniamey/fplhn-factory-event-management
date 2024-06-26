package com.portalevent.util;

import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class IdentityValidation {

    public static SecretKey generateJwtSecretKey(String clientId, String clientSecret) {
        String originalString = clientId + clientSecret;
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-512 algorithm not available. Check your JDK configuration.", e);
        }
        byte[] hashed = md.digest(originalString.getBytes(StandardCharsets.UTF_8));
        return Keys.hmacShaKeyFor(hashed);
    }

}
