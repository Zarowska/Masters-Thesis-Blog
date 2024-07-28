package blog.cirkle.app.exception;

import org.springframework.http.HttpStatus;

public class NotImplementedException extends CirkleException {

	public NotImplementedException() {
		this("Feature not implemented yet", null);
	}

	public NotImplementedException(String message) {
		this(message, null);
	}

	public NotImplementedException(String message, Throwable cause) {
		super(message, cause, HttpStatus.NOT_IMPLEMENTED);
	}
}
