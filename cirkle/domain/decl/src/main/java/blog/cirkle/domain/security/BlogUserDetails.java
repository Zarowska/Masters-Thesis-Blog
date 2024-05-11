package blog.cirkle.domain.security;

import java.util.List;
import java.util.UUID;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class BlogUserDetails extends User {

	public static final UUID SYSTEM_USER_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");
	public static final BlogUserDetails SYSTEM_USER = new BlogUserDetails(SYSTEM_USER_ID, "system@cirkle.blog", true,
			"-1");
	@Getter
	private final UUID id;
	@Getter
	private final boolean admin;

	public BlogUserDetails(UUID id, String email, boolean isAdmin, String password) {
		super(email, password, true, true, true, true, List.of(new SimpleGrantedAuthority("ROLE_USER")));
		this.id = id;
		this.admin = isAdmin;
	}
}
