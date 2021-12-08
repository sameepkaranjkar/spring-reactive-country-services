package com.app.country.error.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CountryNotFoundAdvice {
	@ResponseBody
	@ExceptionHandler(CountryNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	ResponseEntity<ErrorDetails> countryNotFoundHandler(CountryNotFoundException ex) {
		return new ResponseEntity<>(new ErrorDetails(new Date(), ex.getMessage(), ex.toString()), HttpStatus.NOT_FOUND);
	}
}
