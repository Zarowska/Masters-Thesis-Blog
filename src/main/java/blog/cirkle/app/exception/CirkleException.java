package blog.cirkle.app.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class CirkleException extends RuntimeException {
	@Getter
	private final HttpStatus status;

	public CirkleException(HttpStatus status) {
		this.status = status;
	}

	public CirkleException(String message, HttpStatus status) {
		super(message);
		this.status = status;
	}

	public CirkleException(String message, Throwable cause, HttpStatus status) {
		super(message, cause);
		this.status = status;
	}

	public CirkleException(Throwable cause, HttpStatus status) {
		super(cause);
		this.status = status;
	}

	public CirkleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
			HttpStatus status) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.status = status;
	}
}
