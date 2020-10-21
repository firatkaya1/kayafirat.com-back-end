package com.kayafirat.exceptions.customExceptions;

import java.time.LocalDateTime;

public class UserIdNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UserIdNotFoundException(String exception) {
        super("There is not id in this record :"+exception);
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
