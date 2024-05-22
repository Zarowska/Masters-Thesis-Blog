package blog.cirkle.api.rest;

import blog.cirkle.domain.exception.BlogException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Map<Class<? extends Throwable>, Integer> exceptionCodes;

	static {
		LinkedHashMap<Class<? extends Throwable>, Integer> map = new LinkedHashMap<>();
		map.put(BadCredentialsException.class, 401);
		map.put(HttpRequestMethodNotSupportedException.class, 400);
		map.put(MissingRequestHeaderException.class, 400);
		map.put(NoResourceFoundException.class, 404);
		map.put(MethodArgumentTypeMismatchException.class, 400);
		exceptionCodes = Collections.unmodifiableMap(map);
	}

	@ExceptionHandler(BlogException.class)
	ResponseEntity<ProblemDetail> blogExceptionHandler(BlogException ex) {
		log.warn(ex.getClass().getCanonicalName());
		HttpStatusCode code = ex.getHttpStatus();
		return ResponseEntity.status(code).body(ProblemDetail.forStatusAndDetail(code, ex.getMessage()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	ResponseEntity<ProblemDetail> validationExceptionHandler(MethodArgumentNotValidException ex) {
		log.warn(ex.getClass().getCanonicalName());
		HttpStatusCode code = HttpStatusCode.valueOf(400);
		return ResponseEntity.status(code).body(ProblemDetail.forStatusAndDetail(code, ex.getMessage()));
	}

	@ExceptionHandler(Exception.class)
	ResponseEntity<ProblemDetail> internalServerErrorHandler(Exception ex) {
		log.warn(ex.getClass().getCanonicalName());
		int status = exceptionCodes.getOrDefault(ex.getClass(), 500);
		HttpStatusCode code = HttpStatusCode.valueOf(status);
		return ResponseEntity.status(code).body(ProblemDetail.forStatusAndDetail(code, ex.getMessage()));
	}
}
