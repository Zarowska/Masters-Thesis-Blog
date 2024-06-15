package blog.cirkle.domain.facade.impl;

import blog.cirkle.domain.entity.participant.User;
import blog.cirkle.domain.facade.AuthFacade;
import blog.cirkle.domain.model.request.EmailValidationRequest;
import blog.cirkle.domain.model.response.AuthenticateResponse;
import blog.cirkle.domain.security.BlogUserDetails;
import blog.cirkle.domain.security.UserContextHolder;
import blog.cirkle.domain.service.JwtService;
import blog.cirkle.domain.service.UserService;
import com.auth0.jwt.interfaces.Payload;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthFacadeImpl implements AuthFacade, ApplicationRunner {
	private final UserService userService;
	private final UserDetailsService userDetailsService;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;

	@Override
	public BlogUserDetails authenticateByUsernameAndPassword(String username, String password) {
		try {
			BlogUserDetails blogUserDetails = (BlogUserDetails) userDetailsService
					.loadUserByUsername(username.toLowerCase());
			if (!passwordEncoder.matches(password, blogUserDetails.getPassword())) {
				throw new BadCredentialsException("Invalid password");
			}
			authenticate(password, blogUserDetails);
			return blogUserDetails;
		} catch (UsernameNotFoundException e) {
			throw new BadCredentialsException("Invalid user and/or password");
		}

	}

	private static void authenticate(String password, BlogUserDetails blogUserDetails) {
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(blogUserDetails,
				password, blogUserDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		log.debug("User {} authenticated", blogUserDetails.getUsername());
	}

	@Override
	public BlogUserDetails authenticateByToken(String token) {
		AtomicReference<BlogUserDetails> userDetails = new AtomicReference<>();
		try {
			UUID userId = UUID.fromString(jwtService.validateToken(token, Payload::getSubject));
			userService.getFindById(userId).map(User::getEmail).map(userDetailsService::loadUserByUsername)
					.map(BlogUserDetails.class::cast).ifPresent(blogUserDetails -> {
						authenticate(token, blogUserDetails);
						userDetails.set(blogUserDetails);
					});
		} catch (Exception e) {
			// ignore
		}
		return userDetails.get();
	}

	@Override
	public String issueTokenForCurrentUser() {
		return UserContextHolder.getCurrentUser().map(this::issueTokenForUser).orElse("");
	}

	@Override
	public AuthenticateResponse validateAndLogin(EmailValidationRequest request) {
		User user = userService.validateEmail(request);
		authenticateByUsernameAndPassword(user.getEmail(), request.getPassword());
		return new AuthenticateResponse(issueTokenForCurrentUser());
	}

	@Override
	@Transactional
	public void run(ApplicationArguments args) throws Exception {
		userService.createDefaultUsers();
	}

	private String issueTokenForUser(BlogUserDetails userDetails) {
		return jwtService.issueToken(userDetails.getId(), builder -> {
		});
	}
}
