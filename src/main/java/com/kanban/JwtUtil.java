package com.kanban;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {



    private final String SECRET_KEY = "A6F34B60BCB12F17A8D2A32796E1E15DA07E7C4301E7B46E63245EED7B8D81A1";
    private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour in milliseconds

    // Generate JWT
    public String generateToken(String username) {
        return Jwts.builder()
        	    .setSubject(username)
        	    .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
        	    .compact();
    }

    // Validate JWT
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Extract Username from JWT
    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}