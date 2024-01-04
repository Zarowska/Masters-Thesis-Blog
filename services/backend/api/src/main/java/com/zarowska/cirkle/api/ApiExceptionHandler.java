package com.zarowska.cirkle.api;

import com.zarowska.cirkle.api.model.ApiError;
import com.zarowska.cirkle.exception.CirkleException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice
@Slf4j
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(CirkleException.class)
	ResponseEntity<ApiError> handleCirckleException(CirkleException exception) {
		ProblemDetail error = ProblemDetail.forStatusAndDetail(exception.getHttpStatus(), exception.getMessage());
		ApiError apiError = new ApiError();
		apiError.setError(error);
		return ResponseEntity.status(exception.getHttpStatus().value()).body(apiError);
	}

	@ExceptionHandler(Exception.class)
	ResponseEntity<ApiError> handleUnknownException(Exception exception) {
		log.error("Unexpected error", exception);
		return handleCirckleException(new CirkleException(exception, HttpStatus.INTERNAL_SERVER_ERROR));
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatusCode status, WebRequest request) {
		return buildResponse(status, request, ex);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		List<String> errors = new ArrayList<String>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}
		ApiError apiError = new ApiError();
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status,
				errors.stream().collect(Collectors.joining(",")));
		apiError.setError(problemDetail);
		return ResponseEntity.status(status).body(apiError);
	}

	private ResponseEntity<Object> buildResponse(HttpStatusCode status, WebRequest request, Exception e) {
		return ResponseEntity.status(status).body(buildApiError(status, request, e));
	}

	private ApiError buildApiError(HttpStatusCode httpStatus, WebRequest webRequest, Exception exception) {
		ApiError apiError = new ApiError();
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, exception.getMessage());
		apiError.setError(problemDetail);
		return apiError;
	}
}
