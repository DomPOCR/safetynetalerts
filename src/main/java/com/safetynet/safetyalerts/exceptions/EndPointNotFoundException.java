package com.safetynet.safetyalerts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EndPointNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -4906835709951605378L;

	public EndPointNotFoundException(String s) {
		super(s);
	}

}
