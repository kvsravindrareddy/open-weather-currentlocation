package com.ravindra.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
/**
 * Custom Error details for Rest API Endpoints
 * @author Veera Shankara Ravindra Reddy Kakarla
 *
 */
@Setter
@Getter
@AllArgsConstructor
public class ErrorDetails {
	/*Custom Status Code*/
	private int statusCode;
	/*Custom Rest endpoint status message*/
	private String msg;
}