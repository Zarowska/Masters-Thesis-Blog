package blog.cirkle.app.api.graphql;

import blog.cirkle.app.api.graphql.client.GraphQlClient;
import blog.cirkle.app.api.rest.AbstractApiTest;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

class RegistrationControllerTest extends AbstractApiTest {

	private ObjectMapper mapper = new ObjectMapper();

	@Test
	void users() {
		GraphQlClient client = new GraphQlClient("http://localhost:" + port + "/graphql");
		client.register("some@email.com", "john doe");
	}
}
