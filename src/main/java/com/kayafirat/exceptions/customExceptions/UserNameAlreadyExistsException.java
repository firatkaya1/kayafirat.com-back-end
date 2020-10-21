package com.kayafirat.exceptions.customExceptions;

import java.time.LocalDateTime;

public class UserNameAlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UserNameAlreadyExistsException(String errorMessage) {
        super("User Name Already Exists :" + errorMessage);
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
