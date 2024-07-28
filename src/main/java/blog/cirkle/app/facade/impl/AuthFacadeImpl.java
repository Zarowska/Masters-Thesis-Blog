package blog.cirkle.app.facade.impl;

import blog.cirkle.app.facade.AuthFacade;
import blog.cirkle.app.model.entity.User;
import blog.cirkle.app.service.JwtService;
import blog.cirkle.app.service.UserService;
import com.auth0.jwt.interfaces.Payload;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthFacadeImpl implements AuthFacade {

	private final UserService userService;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;

	@Override
	public User authenticateByToken(String token) {
		AtomicReference<User> userDetails = new AtomicReference<>();
		try {
			UUID userId = UUID.fromString(jwtService.validateToken(token, Payload::getSubject));
			userService.findByIdOptional(userId).map(User::getEmail).flatMap(userService::findByEmail)
					.ifPresent(blogUserDetails -> {
						authenticate(token, blogUserDetails);
						userDetails.set(blogUserDetails);
					});
		} catch (Exception e) {
			// ignore
		}
		return userDetails.get();
	}

	@Override
	public User authenticateByUsernameAndPassword(String username, String password) {
		try {
			User user = userService.findByEmail(username.toLowerCase())
					.orElseThrow(() -> new UsernameNotFoundException("User not found"));
			if (!passwordEncoder.matches(password, user.getPasswordHash())) {
				throw new BadCredentialsException("Invalid password");
			}
			authenticate(password, user);
			return user;
		} catch (UsernameNotFoundException e) {
			throw new BadCredentialsException("Invalid user and/or password");
		}
	}

	@Override
	public String issueTokenForCurrentUser() {
		return issueTokenForUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
	}

	private static void authenticate(String password, User user) {
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, password,
				Set.of(new SimpleGrantedAuthority("ROLE_USER")));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		log.debug("User {} authenticated", user.getEmail());
	}

	private String issueTokenForUser(User user) {
		return jwtService.issueToken(user.getId(), builder -> {
		});
	}
}
