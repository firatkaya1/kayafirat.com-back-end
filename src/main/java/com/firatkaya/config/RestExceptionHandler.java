package com.firatkaya.config;

import java.time.LocalDateTime;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
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
	 * 67 Method Cant Supportted
	 * 68 Media Type Cant Acceptable
	 * 69 Missing Path Variable
	 * 70 Servlet Request Binding
	 * 71 Conversion Not Supportted
	 * 72 Type Mismatch
	 * 73 Missing Servlet Request Part
	 * 74 Bind Exception
	 * 75 Async Request Time Out
	 * 
	 * 
	 */
	
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		APIError apiError = new APIError.Builder()
								.httpStatus(status)
								.errorCode(67)
								.message("HTTP method can't supportted")
								.timeStamp(LocalDateTime.now())
								.path(request.toString()).build();
		return buildResponseEntity(apiError);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		APIError apiError = new APIError.Builder()
								.httpStatus(status)
								.errorCode(68)
								.message("HTTP Media Type can't acceptable")
								.timeStamp(LocalDateTime.now())
								.path(request.toString()).build();
		return buildResponseEntity(apiError);
	}

	@Override
	protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		APIError apiError = new APIError.Builder()
								.httpStatus(status)
								.errorCode(69)
								.message("Missing path variable")
								.timeStamp(LocalDateTime.now())
								.path(request.toString()).build();
		return buildResponseEntity(apiError);
	}

	@Override
	protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		APIError apiError = new APIError.Builder()
								.httpStatus(status)
								.errorCode(70)
								.message("Servlet Request Binding")
								.timeStamp(LocalDateTime.now())
								.path(request.toString()).build();
		return buildResponseEntity(apiError);
	}

	@Override
	protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		APIError apiError = new APIError.Builder()
								.httpStatus(status)
								.errorCode(71)
								.message("Conversion Not Supportted")
								.timeStamp(LocalDateTime.now())
								.path(request.toString()).build();
		return buildResponseEntity(apiError);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		APIError apiError = new APIError.Builder()
								.httpStatus(status)
								.errorCode(72)
								.message("Type Error,Please be sure which type we using in these fields")
								.timeStamp(LocalDateTime.now())
								.path(request.toString()).build();
		return buildResponseEntity(apiError);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		APIError apiError = new APIError.Builder()
								.httpStatus(status)
								.errorCode(73)
								.message("Missing file or parameter, please check your body and header")
								.timeStamp(LocalDateTime.now())
								.path(request.toString()).build();
		return buildResponseEntity(apiError);
	}

	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		APIError apiError = new APIError.Builder()
								.httpStatus(status)
								.errorCode(74)
								.message("Bind Exception")
								.timeStamp(LocalDateTime.now())
								.path(request.toString()).build();
		return buildResponseEntity(apiError);
	}

	@Override
	protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex,
			HttpHeaders headers, HttpStatus status, WebRequest webRequest) {
		APIError apiError = new APIError.Builder()
								.httpStatus(status)
								.errorCode(75)
								.message("Async Request time out.")
								.timeStamp(LocalDateTime.now())
								.path(webRequest.toString()).build();
		return buildResponseEntity(apiError);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		// TODO Auto-generated method stub
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		APIError apiError = new APIError.Builder()
								.httpStatus(status)
								.errorCode(62)
								.message("HTTPMEDİATYPE NOT SUPPORTTED JSON")
								.path(request.toString()).build();
		return buildResponseEntity(apiError);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		APIError apiError = new APIError.Builder()
								.httpStatus(status)
								.errorCode(63)
								.message(ex.getParameterName() + "parameter is missing")
								.path(request.toString()).build();
		return buildResponseEntity(apiError);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		APIError apiError = new APIError.Builder()
								.httpStatus(status)
								.errorCode(64)
								.message("Cant read JSON format")
								.path(request.toString()).build();
		return buildResponseEntity(apiError);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		APIError apiError = new APIError.Builder()
								.httpStatus(status)
								.errorCode(65)
								.message("Cant Write JSON")
								.path(request.toString()).build();
		return buildResponseEntity(apiError);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		APIError apiError = new APIError.Builder()
								.httpStatus(status)
								.errorCode(21)
								.message("Validation Error")
								.path(request.toString()).build();
		return buildResponseEntity(apiError);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		APIError apiError = new APIError.Builder()
								.httpStatus(status)
								.errorCode(66)
								.message(String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()))
								.path(request.toString()).build();
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
