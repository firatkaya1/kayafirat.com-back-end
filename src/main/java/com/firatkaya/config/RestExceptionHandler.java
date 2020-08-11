package com.firatkaya.config;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.firatkaya.exceptions.UserNotFoundException;
import com.firatkaya.model.APIError;

/**
 * @author kaya
 *
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler{

	
	/*
	 * Error Codes 
	 * 10 - UserNotFound
	 * 
	 * 
	 * 
	 */
	
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		APIError apiError = new APIError.Builder()
				.httpStatus(status)
				.message("HTTPMEDİATYPE NOT SUPPORTTED JSON")
				.build();
		return buildResponseEntity(apiError);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		APIError apiError = new APIError.Builder()
				.httpStatus(status)
				.message(ex.getParameterName() + "parameter is missing")
				.build();
		return buildResponseEntity(apiError);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		APIError apiError = new APIError.Builder()
				.httpStatus(status)
				.message("Cant read JSON format")
				.build();
		return buildResponseEntity(apiError);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		APIError apiError = new APIError.Builder()
				.httpStatus(status)
				.message("Cant Write JSON")
				.build();
		return buildResponseEntity(apiError);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		APIError apiError = new APIError.Builder()
									.httpStatus(status)
									.message("Validation Error")
									.build();
		return buildResponseEntity(apiError);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		APIError apiError = new APIError.Builder()
				.httpStatus(status)
				.message(String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()))
				.build();
		
		return buildResponseEntity(apiError);
	}

	private ResponseEntity<Object> buildResponseEntity(APIError apiError) {
	        return new ResponseEntity<>(apiError, apiError.getStatus());
	}
	
	@ExceptionHandler(com.firatkaya.exceptions.UserNotFoundException.class)
    protected ResponseEntity<Object> handleConstraintViolation(UserNotFoundException ex) {
       APIError apiError = new APIError.Builder()
    		   .httpStatus(HttpStatus.NOT_FOUND)
    		   .errorCode(10)
    		   .message(ex.getMessage())
    		   .timeStamp(ex.getTime())
    		   .build();
		return buildResponseEntity(apiError);
    }
	
	
	
	
}
