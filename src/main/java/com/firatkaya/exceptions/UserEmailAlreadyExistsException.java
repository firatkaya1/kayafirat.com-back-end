package com.firatkaya.exceptions;

import java.time.LocalDateTime;

public class UserEmailAlreadyExistsException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public UserEmailAlreadyExistsException(String errorMessage) {
		super("User Email Already Exists :"+errorMessage);
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
