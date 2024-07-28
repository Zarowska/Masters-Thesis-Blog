package blog.cirkle.app.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends CirkleException {

	public BadRequestException() {
		this(null, null);
	}

	public BadRequestException(String message) {
		this(message, null);
	}

	public BadRequestException(String message, Throwable cause) {
		super(message, cause, HttpStatus.BAD_REQUEST);
	}
}
