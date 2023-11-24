package org.task.task2;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.*;
import java.util.Date;

public class TokenProvider {
    private static final Key SIGNING_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Generate a token using the provided signing key
    public static String generateToken(String subject, long expirationMillis, Key signingKey) {
        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // Validate and parse a token using the provided signing key
    public static Claims parseToken(String token, Key signingKey) {
        try {
            return Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token).getBody();
        } catch (JwtException e) {
            // Log or handle the exception as needed
            return null;
        }
    }

    // Example usage
    public static void main(String[] args) {
        String subject = "user123";
        long expirationMillis = 3600000; // 1 hour

        String tokenString = generateToken(subject, expirationMillis, SIGNING_KEY);
        System.out.println("Generated Token: " + tokenString);

        Claims claims = parseToken(tokenString, SIGNING_KEY);
        if (claims != null) {
            System.out.println("Subject: " + claims.getSubject());
            System.out.println("Expiration: " + claims.getExpiration());
        } else {
            System.out.println("Token validation failed");
        }
    }
}