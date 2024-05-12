package blog.cirkle.domain.exception;

import org.springframework.http.HttpStatus;

public class NotAllowedException extends BlogException {

	public NotAllowedException(String message) {
		this(message, null);
	}

	public NotAllowedException(String message, Throwable cause) {
		super(message, cause, HttpStatus.FORBIDDEN);
	}
}
