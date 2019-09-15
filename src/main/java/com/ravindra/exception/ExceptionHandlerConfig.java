package com.ravindra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Exception handler class to handle custom exceptions for restful application
 * 
 * @author Veera Shankara RavindraReddy Kakarla
 */
@RestControllerAdvice
public class ExceptionHandlerConfig extends ResponseEntityExceptionHandler {

	/**
	 * Custom Exception handler method to return the custom status code for
	 * ServiceUnavilableException
	 * 
	 * @param ServiceUnavilableException
	 * @return ResponseEntity<ErrorDetails>
	 */
	@ExceptionHandler(ServiceUnavilableException.class)
	public ResponseEntity<ErrorDetails> handleServiceUnavailableException(ServiceUnavilableException e) {
		ErrorDetails error = new ErrorDetails(404, e.toString());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	/**
	 * Custom Exception handler method to handle if the Comic service not found
	 * 
	 * @param ServiceUnavilableException
	 * @return ResponseEntity<ErrorDetails>
	 */
	@ExceptionHandler(ServiceNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleComicServiceNotFoundException(ServiceNotFoundException e) {
		ErrorDetails error = new ErrorDetails(404, e.toString());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
}