package blog.cirkle.app.api.rest;

import blog.cirkle.app.exception.CirkleException;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

	@ExceptionHandler(CirkleException.class)
	ResponseEntity<ProblemDetail> handleCirkleException(CirkleException e) {
		ProblemDetail body = ProblemDetail.forStatus(e.getStatus());
		body.setDetail(e.getMessage());
		return ResponseEntity.status(e.getStatus()).body(body);
	}
}
