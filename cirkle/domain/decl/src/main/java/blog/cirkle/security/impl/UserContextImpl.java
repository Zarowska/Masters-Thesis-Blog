package blog.cirkle.security.impl;

import blog.cirkle.security.BlogUserDetails;
import blog.cirkle.security.UserContext;
import java.time.Instant;
import java.util.function.Supplier;

public class UserContextImpl implements UserContext {
	private static final Supplier<Instant> DEFAULT_TIMESTAMPT_STRATEGY = () -> Instant.now();

	private final Supplier<Instant> timeStampSupplier;
	private final BlogUserDetails userDetails;

	public UserContextImpl(BlogUserDetails userDetails) {
		this(userDetails, DEFAULT_TIMESTAMPT_STRATEGY);
	}

	public UserContextImpl(BlogUserDetails userDetails, Instant fixedTimestamp) {
		this(userDetails, () -> fixedTimestamp);
	}

	public UserContextImpl(BlogUserDetails userDetails, Supplier<Instant> timeStamptStrategy) {
		this.userDetails = userDetails;
		this.timeStampSupplier = timeStamptStrategy;
	}

	@Override
	public Instant now() {
		return timeStampSupplier.get();
	}

	@Override
	public BlogUserDetails currentUser() {
		return userDetails;
	}
}
