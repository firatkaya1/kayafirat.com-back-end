package com.firatkaya.model;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class APIError {
	
	public static class Builder {
			
			private HttpStatus status;
			private int errorCode;
			private String errorMessage;
			private LocalDateTime timestamp;
			private String path;
			
			public Builder httpStatus(HttpStatus status) {
				this.status = status;
				return this;
			}
			public Builder errorCode(int errorCode) {
				this.errorCode = errorCode;
				return this;
			}
			public Builder message(String errorMessage) {
				this.errorMessage = errorMessage;
				return this;
			}
			public Builder timeStamp(LocalDateTime timestamp) {
		        this.timestamp = timestamp;
				return this;
			}
			public Builder path(String path) {
		        this.path = path.substring(path.indexOf("=") + 1,path.indexOf(";"));
				return this;
			}
			public APIError build() {
					return new APIError(this);
			}
			
		}
	
	private HttpStatus status;
	private int errorCode;
	private String errorMessage;
	private LocalDateTime timestamp;
	private String path;
	
	public APIError(Builder build) {
		this.status = build.status;
		this.errorCode = build.errorCode;
		this.errorMessage = build.errorMessage;
		this.timestamp = build.timestamp;
		this.path = build.path;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public int getErrorCode() {
		return errorCode;
	}
	
	public String getPath() {
		return path;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}



	
	
	
}
