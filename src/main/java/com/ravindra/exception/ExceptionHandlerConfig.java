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
	 * Custom Exception handler method to handle if the service not found
	 * 
	 * @param ServiceUnavilableException
	 * @return ResponseEntity<ErrorDetails>
	 */
	@ExceptionHandler(ServiceNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleComicServiceNotFoundException(ServiceNotFoundException e) {
		ErrorDetails error = new ErrorDetails(602, e.toString());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	/**
	 * Custom Exception handler method to handle if No data found
	 * 
	 * @param NoDataFoundException
	 * @return ResponseEntity<ErrorDetails>
	 */
	@ExceptionHandler(NoDataFoundException.class)
	public ResponseEntity<ErrorDetails> noDataFoundException(NoDataFoundException e) {
		ErrorDetails error = new ErrorDetails(603, e.toString());
		return new ResponseEntity<>(error, HttpStatus.NO_CONTENT);
	}

	/**
	 * Custom Exception handler method to handle if the input is invalid
	 * 
	 * @param InvalidInputException
	 * @return ResponseEntity<ErrorDetails>
	 */
	@ExceptionHandler(InvalidInputException.class)
	public ResponseEntity<ErrorDetails> noDataFoundException(InvalidInputException e) {
		ErrorDetails error = new ErrorDetails(604, e.toString());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
}