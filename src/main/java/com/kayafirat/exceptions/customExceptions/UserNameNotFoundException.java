package com.kayafirat.exceptions.customExceptions;

import java.time.LocalDateTime;

public class UserNameNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UserNameNotFoundException(String username) {
        super("User Name Not Found :" + username);
    }

    @Override
    public String getLocalizedMessage() {
        return super.getLocalizedMessage();
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public LocalDateTime getTime() {
        return LocalDateTime.now();
    }

}
