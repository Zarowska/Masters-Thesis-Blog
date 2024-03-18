package com.zarowska.cirkle.exception;

import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends CirkleException {

	public ResourceNotFoundException(String resourceType, String details) {
		super("Resource of type %s (%s) not found".formatted(resourceType, details), HttpStatus.NOT_FOUND);
	}

	public ResourceNotFoundException(String resourceType, Map<String, Object> params) {
		this(resourceType, params.entrySet().stream().map(entry -> "%s=%s".formatted(entry.getKey(), entry.getValue()))
				.collect(Collectors.joining(",")));
	}
}
