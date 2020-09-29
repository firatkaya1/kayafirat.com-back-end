package com.firatkaya.exceptions.customExceptions;

import java.time.LocalDateTime;

public class MailException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public MailException(String exception) {
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
