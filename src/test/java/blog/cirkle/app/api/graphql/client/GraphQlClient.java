package blog.cirkle.app.api.graphql.client;

import static blog.cirkle.app.api.graphql.client.dsl.DSL.*;

import blog.cirkle.app.api.graphql.client.dsl.Operation;
import blog.cirkle.app.api.graphql.model.auth.NewUser;
import blog.cirkle.app.api.rest.client.exception.ClientResponseException;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class GraphQlClient {

	private final String basePath;
	private final RestTemplate restTemplate;
	private AtomicReference<String> token = new AtomicReference<>();

	public GraphQlClient(String basePath) {
		this.basePath = basePath;
		restTemplate = new RestTemplate();
	}

	public void register(String email, String fullName) {
		NewUser newUser = doRegisterCall(email, fullName);

		Operation operation = mutation().withField(operation("resetPassword")
				.withArguments(stringOp("passwordResetToken").withAlias("token"), stringOp("password")));
	}

	private NewUser doRegisterCall(String email, String fullName) {
		Operation operation = mutation().withField(operation("registerUser")
				.withArgument(objectOp("input").append(stringOp("email")).append(stringOp("fullName")))
				.withFields("id", "username", "fullName", "avatarUrl", "passwordResetId"));

		return doCall(operation.build(Map.of("email", email, "fullName", fullName)), NewUser.class);
	}

	private <OUT> OUT doCall(String query, Class<OUT> clazz) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Accept", "application/json");

		if (token.get() != null) {
			headers.add("Authorization", "Bearer " + token.get());
		}
		Map<String, Object> requestBody = Map.of("query", query, "variables", Map.of());

		RequestEntity<Map<String, Object>> request = new RequestEntity<>(requestBody, headers, HttpMethod.POST,
				URI.create(basePath));
		ResponseEntity<OUT> response = restTemplate.exchange(request, clazz);

		if (response.getStatusCode().is2xxSuccessful()) {
			return response.getBody();
		} else {
			throw new ClientResponseException(ProblemDetail.forStatus(response.getStatusCode()));
		}
	}
}
