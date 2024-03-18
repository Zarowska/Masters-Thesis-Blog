package com.zarowska.cirkle.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zarowska.cirkle.api.model.ApiError;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	ObjectMapper mapper = new ObjectMapper();

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		ApiError apiResponse = new ApiError();
		apiResponse.setError(ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED));
		response.setStatus(401);
		response.getOutputStream().write(mapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(apiResponse));
	}
}
