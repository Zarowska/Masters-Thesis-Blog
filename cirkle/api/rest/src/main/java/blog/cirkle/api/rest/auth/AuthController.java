package blog.cirkle.api.rest.auth;

import blog.cirkle.domain.facade.AuthFacade;
import blog.cirkle.domain.model.response.AuthenticateResponse;
import blog.cirkle.domain.security.BlogUserDetails;
import java.util.Base64;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

	public static final String BASIC_PREFIX = "Basic ";
	public static final String BEARER_PREFIX = "Bearer ";

	private final AuthFacade authFacade;

	@PostMapping
	ResponseEntity<AuthenticateResponse> authenticate(
			@RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String token) {
		return Stream
				.of(extractWithPrefix(BASIC_PREFIX, token).map(this::doBasicAuth),
						extractWithPrefix(BEARER_PREFIX, token).map(this::doJwtAuth))
				.filter(Optional::isPresent).map(Optional::get).findFirst()
				.orElseGet(() -> ResponseEntity.status(401).build());
	}

	private Optional<String> extractWithPrefix(String prefix, String token) {
		if (token != null && token.startsWith(prefix) && token.length() > prefix.length()) {
			return Optional.of(token.substring(prefix.length()));
		} else {
			return Optional.empty();
		}
	}

	private ResponseEntity<AuthenticateResponse> doBasicAuth(String token) {
		String[] parts = new String(Base64.getDecoder().decode(token)).split(":", -1);
		if (parts.length != 2) {
			throw new IllegalArgumentException("Provided invalid Basic auth token");
		}
		return authenticateAndIssueToken(() -> authFacade.authenticateByUsernameAndPassword(parts[0], parts[1]));
	}

	private ResponseEntity<AuthenticateResponse> doJwtAuth(String token) {
		return authenticateAndIssueToken(() -> authFacade.authenticateByToken(token));
	}

	private ResponseEntity<AuthenticateResponse> authenticateAndIssueToken(Supplier<BlogUserDetails> authenticator) {
		authenticator.get();
		return ResponseEntity.ok(new AuthenticateResponse(authFacade.issueTokenForCurrentUser()));
	}
}
