package com.kayafirat.exceptions.customExceptions;

import java.time.LocalDateTime;

public class GoogleCaptchaNotValidException  extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public GoogleCaptchaNotValidException(String errorMessage) {
        super("Error : "+errorMessage);
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
