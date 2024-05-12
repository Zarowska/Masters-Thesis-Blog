package blog.cirkle.domain.security;

import java.time.Instant;

public interface UserContext {
	Instant now();

	BlogUserDetails currentUser();
}
