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

import com.firatkaya.exceptions.CommentNotFoundException;
import com.firatkaya.exceptions.EmailException;
import com.firatkaya.exceptions.PostNotFoundException;
import com.firatkaya.exceptions.UnknownOrderedRequestException;
import com.firatkaya.exceptions.UserAlreadyExistsException;
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
	 * 1 - UserAlready Exists
	 * 2 - UserNotFound
	 * 21-30 User Register Validation Errors
	 * 31-40 Post Errors
	 * 41-50 Comment Errors
	 * 51-60 Email Errors
	 * 62 NOT SUPPORTED JSON
	 * 63 PARAMETER İS MİSSİNG
	 * 64 Cant Read JSON FORMAT
	 * 65 Cant WRİTE JSON
	 * 66 Method cant find
	 */
	
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		APIError apiError = new APIError.Builder()
				.httpStatus(status)
				.errorCode(62)
				.message("HTTPMEDİATYPE NOT SUPPORTTED JSON")
				.build();
		return buildResponseEntity(apiError);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		APIError apiError = new APIError.Builder()
				.httpStatus(status)
				.errorCode(63)
				.message(ex.getParameterName() + "parameter is missing")
				.build();
		return buildResponseEntity(apiError);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		APIError apiError = new APIError.Builder()
				.httpStatus(status)
				.errorCode(64)
				.message("Cant read JSON format")
				.build();
		return buildResponseEntity(apiError);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		APIError apiError = new APIError.Builder()
				.httpStatus(status)
				.errorCode(65)
				.message("Cant Write JSON")
				.build();
		return buildResponseEntity(apiError);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		APIError apiError = new APIError.Builder()
									.httpStatus(status)
									.errorCode(21)
									.message("Validation Error")
									.build();
		return buildResponseEntity(apiError);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		APIError apiError = new APIError.Builder()
				.httpStatus(status)
				.errorCode(66)
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
    		   .errorCode(2)
    		   .message(ex.getMessage())
    		   .timeStamp(ex.getTime())
    		   .build();
		return buildResponseEntity(apiError);
    }
	
	@ExceptionHandler(com.firatkaya.exceptions.UserAlreadyExistsException.class)
    protected ResponseEntity<Object> handleConstraintViolation(UserAlreadyExistsException ex) {
       APIError apiError = new APIError.Builder()
    		   .httpStatus(HttpStatus.CONFLICT)
    		   .errorCode(1)
    		   .message(ex.getMessage())
    		   .timeStamp(ex.getTime())
    		   .build();
		return buildResponseEntity(apiError);
    }
	
	@ExceptionHandler(com.firatkaya.exceptions.PostNotFoundException.class)
    protected ResponseEntity<Object> handleConstraintViolation(PostNotFoundException ex) {
       APIError apiError = new APIError.Builder()
    		   .httpStatus(HttpStatus.NOT_FOUND)
    		   .errorCode(31)
    		   .message(ex.getMessage())
    		   .timeStamp(ex.getTime())
    		   .build();
		return buildResponseEntity(apiError);
    }
	
	@ExceptionHandler(com.firatkaya.exceptions.CommentNotFoundException.class)
    protected ResponseEntity<Object> handleConstraintViolation(CommentNotFoundException ex) {
       APIError apiError = new APIError.Builder()
    		   .httpStatus(HttpStatus.NOT_FOUND)
    		   .errorCode(41)
    		   .message(ex.getMessage())
    		   .timeStamp(ex.getTime())
    		   .build();
		return buildResponseEntity(apiError);
    }

	@ExceptionHandler(com.firatkaya.exceptions.EmailException.class)
    protected ResponseEntity<Object> handleConstraintViolation(EmailException ex) {
       APIError apiError = new APIError.Builder()
    		   .httpStatus(HttpStatus.NOT_FOUND)
    		   .errorCode(51)
    		   .message(ex.getMessage())
    		   .timeStamp(ex.getTime())
    		   .build();
		return buildResponseEntity(apiError);
    }
	@ExceptionHandler(com.firatkaya.exceptions.UnknownOrderedRequestException.class)
    protected ResponseEntity<Object> handleConstraintViolation(UnknownOrderedRequestException ex) {
       APIError apiError = new APIError.Builder()
    		   .httpStatus(HttpStatus.NOT_FOUND)
    		   .errorCode(32)
    		   .message(ex.getMessage())
    		   .timeStamp(ex.getTime())
    		   .build();
		return buildResponseEntity(apiError);
    }
	
}
