package com.safetynet.safetyalerts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DataAlreadyExistException extends RuntimeException {

	private static final long serialVersionUID = 884722897606411587L;

	public DataAlreadyExistException(String message) {

		super(message);

	}

}
