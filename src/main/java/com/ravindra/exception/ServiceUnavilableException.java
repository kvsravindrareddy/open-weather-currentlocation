package com.ravindra.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * This is to throw exception if the external is not avaialble.
 * @author Veera Shankara Ravindra Reddy Kakarla
 *
 */
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ServiceUnavilableException extends RuntimeException {
	private static final long serialVersionUID = 2694505109822438003L;
	private String msg;
}