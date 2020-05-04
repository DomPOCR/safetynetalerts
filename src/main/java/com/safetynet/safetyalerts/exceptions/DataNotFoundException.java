package com.safetynet.safetyalerts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DataNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -7009904729315046248L;

	public DataNotFoundException(String message) {

		super(message);

	}

}
