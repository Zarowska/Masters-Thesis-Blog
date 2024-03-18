package com.zarowska.cirkle.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends CirkleException {
	public UnauthorizedException() {
		super(HttpStatus.UNAUTHORIZED);
	}

	public UnauthorizedException(String message) {
		super(message, HttpStatus.UNAUTHORIZED);
	}

}
