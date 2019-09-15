package com.ravindra.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
/**
 * Created class to handle the Invalid input for all the input parameters
 * @author Veera Shankara Ravindra Reddy Kakarla
 *
 */
@Setter
@Getter
@AllArgsConstructor
public class InvalidInputException extends RuntimeException {
	private static final long serialVersionUID = -827580034692616602L;
	private String msg;
}