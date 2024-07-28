package blog.cirkle.app.api.rest;

import blog.cirkle.app.api.rest.model.AuthenticateResponse;
import blog.cirkle.app.exception.BadRequestException;
import blog.cirkle.app.facade.AuthFacade;
import blog.cirkle.app.model.entity.User;
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
@RequestMapping(path = "/public/api/auth")
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
		return Stream.of(extractWithPrefix(BEARER_PREFIX, token).map(this::doJwtAuth)).filter(Optional::isPresent)
				.map(Optional::get).findFirst().orElseGet(() -> ResponseEntity.status(401).build());
	}

	private Optional<String> extractWithPrefix(String prefix, String token) {
		if (token != null && token.startsWith(prefix) && token.length() > prefix.length()) {
			return Optional.of(token.substring(prefix.length()));
		} else {
			return Optional.empty();
		}
	}

	@Operation(summary = "Authenticate with basic credetials", description = "Returns authentication response", operationId = "authByForm", tags = {
			"auth"})
	@PostMapping(path = "/basic")
	public ResponseEntity<AuthenticateResponse> doBasicAuth(@RequestParam String username,
			@RequestParam String password) {
		return authenticateAndIssueToken(() -> authFacade.authenticateByUsernameAndPassword(username, password));
	}

	private ResponseEntity<AuthenticateResponse> doJwtAuth(String token) {
		return authenticateAndIssueToken(() -> authFacade.authenticateByToken(token));
	}

	private ResponseEntity<AuthenticateResponse> authenticateAndIssueToken(Supplier<User> authenticator) {
		authenticator.get();
		return ResponseEntity.ok(new AuthenticateResponse(authFacade.issueTokenForCurrentUser()));
	}
}
