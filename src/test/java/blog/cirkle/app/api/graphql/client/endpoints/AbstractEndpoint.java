package blog.cirkle.app.api.graphql.client.endpoints;

import blog.cirkle.app.api.graphql.client.GraphQlClient;
import blog.cirkle.app.api.graphql.client.dsl.Operation;
import com.google.gson.JsonElement;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@RequiredArgsConstructor
public class AbstractEndpoint {
	protected final ObjectMapper mapper = new ObjectMapper();
	protected final GraphQlClient client;

	@SneakyThrows
	protected <T> T extract(Map<Operation, JsonElement> results, Operation operation, Class<T> clazz) {
		return mapper.readValue(results.get(operation).toString(), clazz);
	}

	protected <T> T doSingleQuery(Map<String, Object> args, Operation operation, Class<T> clazz) {
		return extract(client.doCall("query", args, operation), operation, clazz);
	}

	protected <T> T doSingleMutation(Map<String, Object> args, Operation operation, Class<T> clazz) {
		return extract(client.doCall("query", args, operation), operation, clazz);
	}

}
