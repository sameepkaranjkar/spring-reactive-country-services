package com.app.country.error.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ClientExceptionAdvice {
	@ResponseBody
	@ExceptionHandler(ClientException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	ResponseEntity<ErrorDetails> clientExceptionHandler(ClientException ex) {
		return new ResponseEntity<>(new ErrorDetails(new Date(), ex.getMessage(), ex.toString()),
				HttpStatus.UNAUTHORIZED);
	}
}
