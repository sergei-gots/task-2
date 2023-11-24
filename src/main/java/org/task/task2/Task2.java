package org.task.task2;

import io.jsonwebtoken.Claims;

public class Task2 {

    public static void task2() {
        String subject = "user123";

        Token tokenString = TokenUtil.getToken(subject);
        System.out.println("Generated or Cached Token: " + tokenString);

        Claims claims = TokenProvider.parseToken(tokenString);
        if (claims != null) {
            System.out.println("Subject: " + claims.getSubject());
            System.out.println("Expiration: " + claims.getExpiration());
        } else {
            System.out.println("Token validation failed");
        }
    }
}
