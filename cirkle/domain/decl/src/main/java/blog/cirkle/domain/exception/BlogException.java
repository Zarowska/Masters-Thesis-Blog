package blog.cirkle.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public abstract class BlogException extends RuntimeException {
	@Getter
	private final HttpStatus httpStatus;

	public BlogException(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public BlogException(String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
	}

	public BlogException(String message, Throwable cause, HttpStatus httpStatus) {
		super(message, cause);
		this.httpStatus = httpStatus;
	}

	public BlogException(Throwable cause, HttpStatus httpStatus) {
		super(cause);
		this.httpStatus = httpStatus;
	}

	public BlogException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
			HttpStatus httpStatus) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.httpStatus = httpStatus;
	}
}
