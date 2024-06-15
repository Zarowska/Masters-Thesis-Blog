package blog.cirkle.domain.service.impl;

import blog.cirkle.domain.entity.participant.User;
import blog.cirkle.domain.security.BlogUserDetails;
import blog.cirkle.domain.security.UserContextHolder;
import blog.cirkle.domain.service.UserService;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.stereotype.Component;

@Component
public class UserServiceHolder {
	private static final AtomicReference<UserService> instance = new AtomicReference<>();

	public UserServiceHolder(UserService userService) {
		instance.set(userService);
	}

	public static Optional<UserService> getInstance() {
		return Optional.ofNullable(instance.get());
	}

	public static Optional<User> currentUser() {
		return getInstance().flatMap(
				service -> UserContextHolder.getCurrentUser().map(BlogUserDetails::getId).flatMap(service::findById));
	}

	public static User currentUserOrNull() {
		return currentUser().orElse(null);
	}
}
