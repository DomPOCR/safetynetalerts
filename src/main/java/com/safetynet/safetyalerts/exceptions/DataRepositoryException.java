package com.safetynet.safetyalerts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DataRepositoryException extends RuntimeException {

	private static final long serialVersionUID = -300682534756461382L;

	public DataRepositoryException(String message, Throwable cause) {

		super(message, cause);
	}

}
