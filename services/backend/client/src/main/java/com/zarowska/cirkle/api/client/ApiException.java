package com.zarowska.cirkle.api.client;

import com.zarowska.cirkle.api.model.ApiError;

public class ApiException extends RuntimeException {
	private final ApiError error;

	public ApiException(ApiError error) {
		this.error = error;
	}
}
