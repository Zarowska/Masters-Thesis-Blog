package blog.cirkle.app.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends CirkleException {

	public ForbiddenException() {
		this(null, null);
	}

	public ForbiddenException(String message) {
		this(message, null);
	}

	public ForbiddenException(String message, Throwable cause) {
		super(message, cause, HttpStatus.FORBIDDEN);
	}

}
