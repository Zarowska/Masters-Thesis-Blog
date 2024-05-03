package blog.cirkle.security;

import java.time.Instant;

public interface UserContext {
	Instant now();

	BlogUserDetails currentUser();
}
