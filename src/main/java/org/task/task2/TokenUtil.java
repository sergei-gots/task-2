package org.task.task2;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/** Client's class
 */
public class TokenUtil implements TokenConstants {
    private static final Map<String, Token> tokenCache = new HashMap<>();

    /**
     * This method is a server function and can be invoked in multiple threads simultaneously
     * This function must be executed no longer than 15 seconds,
     * possibly minimizing the amount of requests to the TokenProvider.getToken()
     * and Token.refresh() which send requests to the third part system and therefore
     * can be explicitly slow or at all be suspended.
     * Generates or retrieves a token from the cache.
     *
     * @return a valid token or null if no token received.
     */
    public static Token getToken(String subject) {

        Token cachedToken = tokenCache.get(subject);

        if (cachedToken != null && cachedToken.getExpirationTime().before(new Date())) {
            return cachedToken;
        }

        Token newToken = TokenProvider.getToken(subject, EXPIRATION_MILLIS);
        tokenCache.put(subject, newToken);
        return newToken;
    }



    public static Token refresh(Token token) {
        if (token.getExpirationTime().getTime() < System.currentTimeMillis() - REFRESH_ALLOWANCE_THRESHOLD) {
            return token;
        }
        Token newToken = TokenProvider.refresh(token, EXPIRATION_MILLIS);
        assert newToken != null;
        tokenCache.put(newToken.getSubject(), newToken);
        return newToken;
    }
}
