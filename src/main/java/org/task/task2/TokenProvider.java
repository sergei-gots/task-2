package org.task.task2;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;


import java.util.Date;

import java.security.Key;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/** Server Class **/
public class TokenProvider implements TokenConstants {
    private static final Key SIGNING_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final Map<String, Token> tokenCache = new ConcurrentHashMap<>();

    // Generate or retrieve a token from the cache
    public static Token getToken(String subject, long expirationMillis) {
        Token cachedToken = tokenCache.get(subject);

        if (cachedToken != null &&
                cachedToken.getExpirationTime().getTime() > System.currentTimeMillis() + REFRESH_NECESSITY_THRESHOLD) {
            return cachedToken;
        }

        Date expirationDate = new Date(System.currentTimeMillis() + expirationMillis);
        String newTokenString = generateToken(subject, expirationDate);
        Token newToken = new Token(newTokenString, subject, expirationDate);
        tokenCache.put(subject, newToken);
        return newToken;

    }

    // Generate a token using the provided signing key
    private static String generateToken(String subject, Date expirationDate) {
        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(expirationDate)
                .signWith(SIGNING_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public static Token refresh(Token token, long expirationMillis) {
        try {
            Claims claims = parseToken(token);
            assert claims != null;

            Date newExpirationDate = new Date(System.currentTimeMillis() + expirationMillis);
            // Update expiration time
            claims.setExpiration(newExpirationDate);

            // Build a new token with the updated claims
            String newTokenString = Jwts.builder()
                    .setClaims(claims)
                    .signWith(SIGNING_KEY, SignatureAlgorithm.HS256)
                    .compact();

            Token newToken = new Token(newTokenString, claims.getSubject(), newExpirationDate);
            tokenCache.put(claims.getSubject(), newToken);
            return newToken;
        } catch (JwtException e) {
            System.err.println("Couldn't refresh. " + e.getMessage());
            return null;
        }
    }
    // Validate and parse a token using the provided signing key
    public static Claims parseToken(Token token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(SIGNING_KEY)
                    .build()
                    .parseClaimsJws(token.getToken()).getBody();
        } catch (JwtException e) {
            System.err.println("Couldn't parse token. " + e.getMessage());
            return null;
        }
    }


}