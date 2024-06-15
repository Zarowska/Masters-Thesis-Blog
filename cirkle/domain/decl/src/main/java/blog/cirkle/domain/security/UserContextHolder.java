package blog.cirkle.domain.security;

import blog.cirkle.domain.security.impl.UserContextImpl;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserContextHolder {
	private static final AtomicReference<UserContextHolder> instance = new AtomicReference<>();
	private static final ThreadLocal<List<UserContext>> threadLocal = new ThreadLocal<>();

	static {
		threadLocal.set(Collections.synchronizedList(new ArrayList<>()));
	}

	private static final List<Supplier<Optional<UserContext>>> userContextProviders = List.of(
			// mockUser
			() -> {
				List<UserContext> mockContexts = Optional.ofNullable(threadLocal.get()).orElse(Collections.emptyList());
				if (mockContexts.isEmpty()) {
					return Optional.empty();
				} else {
					return mockContexts.stream().findFirst();
				}
			},
			// defaultUser
			() -> Optional.ofNullable(SecurityContextHolder.getContext()).map(SecurityContext::getAuthentication)
					.map(Authentication::getPrincipal).filter(BlogUserDetails.class::isInstance)
					.map(it -> (BlogUserDetails) it).map(UserContextImpl::new),
			// systemUser
			() -> Optional.of(new UserContextImpl(BlogUserDetails.SYSTEM_USER)));

	public static UserContextHolder getInstance() {
		return instance.get();
	}

	public static Optional<BlogUserDetails> getCurrentUser() {
		return getUserContext().map(UserContext::currentUser);
	}

	public static BlogUserDetails getCurrentUserOrThrow() {
		return getUserContext().map(UserContext::currentUser).orElseThrow();
	}

	public static Optional<Instant> getNow() {
		return getUserContext().map(UserContext::now);
	}

	private static Optional<UserContext> getUserContext() {
		return userContextProviders.stream().map(Supplier::get).filter(Optional::isPresent).findFirst()
				.map(Optional::get);
	}
}
