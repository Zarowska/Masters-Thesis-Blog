package blog.cirkle.app.api.graphql.client;

import static blog.cirkle.app.api.graphql.client.dsl.DSL.*;

import blog.cirkle.app.api.graphql.client.dsl.Operation;
import blog.cirkle.app.api.graphql.client.endpoints.AuthEndpoint;
import blog.cirkle.app.api.graphql.client.endpoints.UserEndpoint;
import blog.cirkle.app.api.rest.client.endpoints.utils.LogUtils;
import blog.cirkle.app.api.rest.client.exception.ClientResponseException;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import lombok.Getter;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class GraphQlClient {

	private final String basePath;
	private final RestTemplate restTemplate;
	@Getter
	private AtomicReference<String> token = new AtomicReference<>();
	@Getter
	private AtomicReference<UUID> userId = new AtomicReference<>();
	@Getter
	private AtomicReference<String> logFileName = new AtomicReference<>();

	public GraphQlClient(String basePath) {
		this.basePath = basePath;
		restTemplate = new RestTemplate();
	}

	public AuthEndpoint authEndpoint() {
		return new AuthEndpoint(this);
	}

	private String buildMutation(Map<String, Object> args, Operation... operations) {
		Operation mutation = mutation();
		for (Operation operation : operations) {
			mutation = mutation.withField(operation);
		}
		return mutation.build(args);
	}

	private String buildQuery(Map<String, Object> args, Operation... operations) {
		Operation query = query();
		for (Operation operation : operations) {
			query = query.withField(operation);
		}
		return query.build(args);
	}

	public Map<Operation, JsonElement> doCall(String type, Map<String, Object> args, Operation... operations) {

		JsonObject jsonObject;
		if (type.equals("mutation")) {
			jsonObject = doCall(buildMutation(args, operations));
		} else if (type.equals("query")) {
			jsonObject = doCall(buildQuery(args, operations));
		} else {
			throw new IllegalArgumentException(type);
		}

		Map<Operation, JsonElement> results = new HashMap<>();
		Arrays.stream(operations).forEach(op -> {
			results.put(op, jsonObject.get(op.getName()));
		});
		return results;
	}

	private JsonObject doCall(String query) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Accept", "application/json");

		if (token.get() != null) {
			headers.add("Authorization", "Bearer " + token.get());
		}
		Map<String, Object> requestBody = Map.of("query", query, "variables", Map.of());

		RequestEntity<Map<String, Object>> request = new RequestEntity<>(requestBody, headers, HttpMethod.POST,
				URI.create(basePath));
		ResponseEntity<String> responseStr = restTemplate.exchange(request, String.class);

		logRequest(request, responseStr);
		if (responseStr.getStatusCode().is2xxSuccessful()) {
			JsonObject response = new Gson().fromJson(responseStr.getBody(), JsonObject.class);
			if (response.has("errors")) {
				ProblemDetail problemDetail = ProblemDetail.forStatus(400);
				problemDetail.setDetail(response.get("errors").toString());
				throw new ClientResponseException(problemDetail);
			}
			return response.get("data").getAsJsonObject();
		} else {
			throw new ClientResponseException(ProblemDetail.forStatus(responseStr.getStatusCode()));
		}
	}

	public void setLogFileName(String logFileName) {
		this.logFileName.set(logFileName);
	}

	public <T> void logRequest(RequestEntity<Map<String, Object>> request, ResponseEntity<String> response) {
		if (logFileName.get() != null) {
			LogUtils.logRequest(logFileName.get(), request, response);
		}
	}

	public void logComment(String comment) {
		if (logFileName.get() != null) {
			LogUtils.appendLog(logFileName.get(), "// " + comment + "\n\n");
		}
	}

	public UserEndpoint user() {
		return new UserEndpoint(this);
	}
}
