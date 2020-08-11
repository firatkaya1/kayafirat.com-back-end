package com.firatkaya.exceptions;

import java.time.LocalDateTime;

public class EmailException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public EmailException(String exception) {
        super("Email could not send to  :"+exception+" address.");

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
