package blog.cirkle.app.api.graphql;

import blog.cirkle.app.api.AbstractApiTest;
import blog.cirkle.app.api.graphql.client.GraphQlClient;
import blog.cirkle.app.api.rest.client.exception.ClientResponseException;
import blog.cirkle.app.utils.UnsafeConsumer;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

public class AbstractGraphQlApiTest extends AbstractApiTest {

	protected @NotNull GraphQlClient createClient() {
		GraphQlClient client = new GraphQlClient("http://localhost:" + port + "/graphql");
		return client;
	}

	protected void asAlice(UnsafeConsumer<GraphQlClient> action) {
		as("Alice", action);
	}

	protected void asMichael(UnsafeConsumer<GraphQlClient> action) {
		as("Michael", action);
	}

	protected void asJessica(UnsafeConsumer<GraphQlClient> action) {
		as("Jessica", action);
	}

	protected void asBob(UnsafeConsumer<GraphQlClient> action) {
		as("Bob", action);
	}

	protected void asCharlie(UnsafeConsumer<GraphQlClient> action) {
		as("Charlie", action);
	}

	protected void asDavid(UnsafeConsumer<GraphQlClient> action) {
		as("David", action);
	}

	protected void asEve(UnsafeConsumer<GraphQlClient> action) {
		as("Eve", action);
	}

	protected void asFrank(UnsafeConsumer<GraphQlClient> action) {
		as("Frank", action);
	}

	@SneakyThrows
	protected void as(String name, UnsafeConsumer<GraphQlClient> action) {
		String email = name + "@cirkle.blog";
		String password = name + "__passw0rd___";
		as(name, email, password, action);
	}

	@SneakyThrows
	protected void as(String name, String email, String password, UnsafeConsumer<GraphQlClient> action) {
		GraphQlClient client = createClient();
		try {
			client.authEndpoint().login(email, password);
		} catch (ClientResponseException e) {
			client.authEndpoint().registerAndResetPassword(email, name, password);
		}
		action.accept(client);
	}
}
