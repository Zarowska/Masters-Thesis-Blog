package com.zarowska.cirkle.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends CirkleException {
	public BadRequestException(String message) {
		super(message, HttpStatus.BAD_REQUEST);
	}

	public BadRequestException(String message, Throwable cause) {
		super(message, cause, HttpStatus.BAD_REQUEST);
	}
}
