package blog.cirkle.domain.exception;

import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends BlogException {

	public ResourceNotFoundException(String resourceName, Map<String, Object> details) {
		this("Resource of type " + resourceName + " with "
				+ details.entrySet().stream().sorted(Map.Entry.comparingByKey())
						.map(it -> "%s=%s".formatted(it.getKey(), it.getValue())).collect(Collectors.joining(","))
				+ " not found");
	}

	public ResourceNotFoundException(String message) {
		super(message, HttpStatus.NOT_FOUND);
	}

	public ResourceNotFoundException(String message, Throwable cause) {
		super(message, cause, HttpStatus.NOT_FOUND);
	}
}
