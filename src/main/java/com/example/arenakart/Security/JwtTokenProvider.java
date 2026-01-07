package com.example.arenakart.Security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt.secret:}")
    private String jwtSecret;

    @Value("${app.jwt.expiration:86400000}")
    private long jwtExpirationMs;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        if (jwtSecret == null || jwtSecret.isEmpty()) {
            // Generate a secure key if none provided
            this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
            System.out.println("WARNING: No JWT secret configured. Using auto-generated key.");
            System.out.println("For production, set app.jwt.secret in application.properties");
        } else {
            // Decode base64 or use direct bytes
            try {
                byte[] keyBytes = Base64.getDecoder().decode(jwtSecret);
                this.secretKey = Keys.hmacShaKeyFor(keyBytes);
            } catch (IllegalArgumentException e) {
                // Not base64, use as UTF-8 string
                byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
                
                // Ensure minimum 64 bytes for HS512
                if (keyBytes.length < 64) {
                    throw new IllegalArgumentException(
                        "JWT secret must be at least 64 bytes (512 bits) for HS512. " +
                        "Current size: " + keyBytes.length + " bytes. " +
                        "Please use a longer secret or base64 encode a 64-byte key."
                    );
                }
                this.secretKey = Keys.hmacShaKeyFor(keyBytes);
            }
        }
    }

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(secretKey, SignatureAlgorithm.HS512)
            .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
