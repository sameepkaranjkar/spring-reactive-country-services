package com.app.country.error.handler;

/**
 * Custom Exception class for Country not Found exception
 * 
 * @author SameepK
 *
 */
public class CountryNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -3987327537375827350L;

	public CountryNotFoundException(String name) {
		super(" Country not found  : " + name);
	}

}
