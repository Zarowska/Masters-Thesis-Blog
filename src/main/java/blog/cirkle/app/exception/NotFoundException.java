package blog.cirkle.app.exception;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;

public class NotFoundException extends CirkleException {

	public NotFoundException() {
		this(null, null);
	}

	public NotFoundException(String message) {
		this(message, null);
	}

	public NotFoundException(String message, Throwable cause) {
		super(message, cause, HttpStatus.NOT_FOUND);
	}

	public static NotFoundException resource(String resourceType, Map<String, Object> details) {
		if (details == null) {
			details = new HashMap<>();
		}
		String detailsString = details.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey))
				.map(it -> "%s=%s".formatted(it.getKey(), it.getValue())).collect(Collectors.joining(",", "(", ")"));
		return new NotFoundException("Resource of type %s%s not found.".formatted(resourceType, detailsString), null);
	}

	public static NotFoundException image(UUID id) {
		return resource("image", Map.of("id", id));
	}

	public static RuntimeException post(UUID postId) {
		return resource("Post", Map.of("id", postId));
	}
}
