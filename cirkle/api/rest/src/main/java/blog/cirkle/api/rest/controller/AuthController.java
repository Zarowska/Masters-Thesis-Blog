package blog.cirkle.api.rest.controller;

import blog.cirkle.domain.exception.BadRequestException;
import blog.cirkle.domain.facade.AuthFacade;
import blog.cirkle.domain.model.response.AuthenticateResponse;
import blog.cirkle.domain.security.BlogUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Base64;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

	public static final String BASIC_PREFIX = "Basic ";
	public static final String BEARER_PREFIX = "Bearer ";

	private final AuthFacade authFacade;

	@Operation(summary = "Authenticate with form", description = "Returns authentication response", operationId = "authByForm", tags = {
			"auth"})
	@PostMapping("/form")
	ResponseEntity<AuthenticateResponse> authenticate(@RequestParam(required = false) String email,
			@RequestParam(required = false) String password, @RequestParam(required = false) String token) {
		String authorization = null;
		if (email != null && password != null) {
			authorization = BASIC_PREFIX
					+ Base64.getEncoder().encodeToString("%s:%s".formatted(email, password).getBytes());
		} else if (token != null) {
			authorization = BEARER_PREFIX + token;
		}

		if (authorization == null) {
			throw new BadRequestException("Expected email with password or token");
		}

		return authenticate(authorization);
	}

	@Operation(summary = "Authenticate", description = "Returns authentication response", operationId = "auth", tags = {
			"auth"})
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
