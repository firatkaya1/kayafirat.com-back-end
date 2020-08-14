package com.firatkaya.exceptions;

import java.time.LocalDateTime;

public class UnknownOrderedRequestException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public UnknownOrderedRequestException() {
        super("Please only use asc or desc otherwise i cant show you anything");
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
