package com.ravindra.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
/**
 * This is currently not used
 * @author Veera Shankara Ravindra Reddy Kakarla
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ServiceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -827580034692616602L;
	private String msg;
}