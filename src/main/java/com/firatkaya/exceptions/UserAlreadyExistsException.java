package com.firatkaya.exceptions;

import java.time.LocalDateTime;

public class UserAlreadyExistsException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public UserAlreadyExistsException(String errorMessage) {
		super("User Already Exists :"+errorMessage);
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
