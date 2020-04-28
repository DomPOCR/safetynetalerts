package com.safetynet.safetyalerts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EndPointNotFoundException extends RuntimeException {

	public EndPointNotFoundException(String s) {
		super(s);
	}

}
