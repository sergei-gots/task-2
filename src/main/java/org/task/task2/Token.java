package org.task.task2;

import java.util.Date;

public class Token implements TokenConstants {
    private final String token;

    private final String subject;
    private final Date expirationTime; // Store the signing key for refreshing

    public Token(String token, String subject, Date expirationTime) {
        this.token = token;
        this.subject = subject;
        this.expirationTime = expirationTime;
    }

    public String getToken() {
        return token;
    }

    public String getSubject() {
        return subject;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }


    public Token refresh() {
        return TokenUtil.refresh(this);
    }


}