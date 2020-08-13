package com.firatkaya.exceptions;

import java.time.LocalDateTime;

public class UserNameNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public UserNameNotFoundException(String exception) {
        super("User Name Not Found :"+exception);

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
