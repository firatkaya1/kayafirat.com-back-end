package com.firatkaya.exceptions;

import java.time.LocalDateTime;

public class CommentNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CommentNotFoundException(String errorMessage) {
		super("There is no comment in this id:"+errorMessage);
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
