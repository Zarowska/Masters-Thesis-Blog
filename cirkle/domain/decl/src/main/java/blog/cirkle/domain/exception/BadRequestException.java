package blog.cirkle.domain.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends BlogException {

	public BadRequestException(String message) {
		this(message, null);
	}

	public BadRequestException(String message, Throwable cause) {
		super(message, cause, HttpStatus.BAD_REQUEST);
	}
}
