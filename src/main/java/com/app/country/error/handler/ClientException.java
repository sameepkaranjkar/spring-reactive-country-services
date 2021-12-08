package com.app.country.error.handler;

public class ClientException extends RuntimeException {
	private static final long serialVersionUID = 1752748561934745901L;

	public ClientException(String message) {
		super(message);
	}
}
