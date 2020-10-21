package com.kayafirat.exceptions.customExceptions;

import java.time.LocalDateTime;

public class UserEmailAlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UserEmailAlreadyExistsException(String emailAddress) {
        super("User Email Already Exists :" + emailAddress);
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
