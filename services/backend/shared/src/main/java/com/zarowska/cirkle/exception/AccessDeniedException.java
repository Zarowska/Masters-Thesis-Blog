package com.zarowska.cirkle.exception;

import org.springframework.http.HttpStatus;

public class AccessDeniedException extends CirkleException {
	public AccessDeniedException() {
		super(HttpStatus.FORBIDDEN);
	}

	public AccessDeniedException(String message) {
		super(message, HttpStatus.UNAUTHORIZED);
	}
}