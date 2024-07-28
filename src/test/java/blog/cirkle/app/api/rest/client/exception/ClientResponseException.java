package blog.cirkle.app.api.rest.client.exception;

import lombok.Getter;
import org.springframework.http.ProblemDetail;

public class ClientResponseException extends RuntimeException {
	@Getter
	private final ProblemDetail problemDetail;

	public ClientResponseException(ProblemDetail problemDetail) {
		super(problemDetail.getDetail());
		this.problemDetail = problemDetail;
	}

	public int getStatus() {
		return problemDetail.getStatus();
	}
}
