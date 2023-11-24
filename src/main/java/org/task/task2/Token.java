package org.task.task2;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class Token {
    private final String token;
    private final Key signingKey; // Store the signing key for refreshing

    public Token(String token, Key signingKey) {
        this.token = token;
        this.signingKey = signingKey;
    }

    public String getToken() {
        return token;
    }

    public Date getExpirationTime() {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token).getBody();
            return claims.getExpiration();
        } catch (JwtException e) {
            // Log or handle the exception as needed
            return null;
        }
    }

    /**In this example, the refresh method takes a new expiration time as a parameter. It parses the existing token, updates the expiration time in the claims, and then generates a new token with the updated claims. The new token is returned as a new Token object.

    The main method in the example demonstrates how to generate a token, access its expiration time, and then refresh the token with a new expiration time. The refreshed token and its new expiration time are printed for verification.

    *Please note that this is just one way to implement token refreshing, and the specifics may vary based on your application's requirements and security considerations.
     **/
    public Token refresh(long expirationMillis) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token).getBody();

            // Update expiration time
            claims.setExpiration(new Date(System.currentTimeMillis() + expirationMillis));

            // Build a new token with the updated claims
            String newToken = Jwts.builder()
                    .setClaims(claims)
                    .signWith(signingKey, SignatureAlgorithm.HS256)
                    .compact();

            return new Token(newToken, signingKey);
        } catch (JwtException e) {
            // Log or handle the exception as needed
            return null;
        }
    }

    // Example usage
    public static void main(String[] args) {
        String subject = "user123";
        long expirationMillis = 3_600_000L; // 1 hour

        Key signingKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String tokenString = TokenProvider.generateToken(subject, expirationMillis, signingKey);
        Token token = new Token(tokenString, signingKey);

        System.out.println("Generated Token: " + token.getToken());

        Date expirationTime = token.getExpirationTime();
        if (expirationTime != null) {
            System.out.println("Expiration Time: " + expirationTime);
        } else {
            System.out.println("Token validation failed");
        }

        // Example of refreshing the token
        long newExpirationMillis = 7200000; // 2 hours
        Token refreshedToken = token.refresh(newExpirationMillis);
        if (refreshedToken != null) {
            System.out.println("Refreshed Token: " + refreshedToken.getToken());
            System.out.println("New Expiration Time: " + refreshedToken.getExpirationTime());
        } else {
            System.out.println("Token refresh failed");
        }
    }
}