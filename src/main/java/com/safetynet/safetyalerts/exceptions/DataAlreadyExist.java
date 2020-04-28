package com.safetynet.safetyalerts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DataAlreadyExist extends RuntimeException {

	public DataAlreadyExist(String message) {

		super(message);

	}

}
