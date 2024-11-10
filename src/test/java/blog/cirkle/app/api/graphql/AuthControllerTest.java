package blog.cirkle.app.api.graphql;

import blog.cirkle.app.api.graphql.client.GraphQlClient;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

class AuthControllerTest extends AbstractGraphQlApiTest {

	private ObjectMapper mapper = new ObjectMapper();

	@Test
	void shouldRegister() {
		GraphQlClient client = createClient();
		client.authEndpoint().registerAndResetPassword("some@email.com", "john doe", "12345");
	}

	@Test
	void shouldLogin() {
		GraphQlClient client = createClient();
		client.setLogFileName("login.log");
		client.authEndpoint().registerAndResetPassword("some@email.com", "john doe", "12345");
		GraphQlClient joe = client.authEndpoint().login("some@email.com", "12345");
	}
}
