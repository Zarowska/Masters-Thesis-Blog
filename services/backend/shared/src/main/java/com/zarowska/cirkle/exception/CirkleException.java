package com.zarowska.cirkle.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class CirkleException extends RuntimeException {

	@Getter
	private final HttpStatus httpStatus;
	public CirkleException() {
		httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	}

	public CirkleException(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public CirkleException(String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
	}

	public CirkleException(String message, Throwable cause, HttpStatus httpStatus) {
		super(message, cause);
		this.httpStatus = httpStatus;
	}

	public CirkleException(Throwable cause, HttpStatus httpStatus) {
		super(cause);
		this.httpStatus = httpStatus;
	}

	public CirkleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
			HttpStatus httpStatus) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.httpStatus = httpStatus;
	}
}
