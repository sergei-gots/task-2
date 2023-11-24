package org.task.task2;

public class TokenUtil {
    /**
     * So that this method is a server function and can be invoked in multiple threads simultaneously
     * This function must be executed no longer than 15 seconds,
     * possibly minimizing the amount of requests to the TokenProvider.getToken()
     * and Token.refresh() which send requests to the third part system and therefore
     * can be explicitly slow or at all be suspended.
     * @return a valid token or null if no token received.
     */
    public Token getToken() {
        return null;
    }
}
