package blog.cirkle.app.api.rest;

import static org.junit.jupiter.api.Assertions.*;

import blog.cirkle.app.api.rest.client.ApiClient;
import blog.cirkle.app.api.rest.client.exception.ClientResponseException;
import blog.cirkle.app.api.rest.model.request.CreateUserDto;
import blog.cirkle.app.utils.UnsafeConsumer;
import java.util.UUID;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"classpath:sql/clear_tables.sql", "classpath:sql/sample_data.sql"})
public abstract class AbstractApiTest {

	@LocalServerPort
	private int port;

	@SneakyThrows
	protected void as(String name, UnsafeConsumer<ApiClient> action) {
		String email = name + "@cirkle.blog";
		String password = name + "__passw0rd___";
		as(name, email, password, action);
	}

	@SneakyThrows
	protected void as(String name, String email, String password, UnsafeConsumer<ApiClient> action) {
		ApiClient client = createClient();
		try {
			client.register(CreateUserDto.builder().email(email).fullName(name).build(), password);
		} catch (ClientResponseException e) {
			client.login(email, password);
		}
		assertTrue(client.isLoggedIn());
		action.accept(client);
	}

	protected void asAlice(UnsafeConsumer<ApiClient> action) {
		as("Alice", action);
	}

	protected void asMichael(UnsafeConsumer<ApiClient> action) {
		as("Michael", action);
	}

	protected void asJessica(UnsafeConsumer<ApiClient> action) {
		as("Jessica", action);
	}

	protected void asBob(UnsafeConsumer<ApiClient> action) {
		as("Bob", action);
	}

	protected void asCharlie(UnsafeConsumer<ApiClient> action) {
		as("Charlie", action);
	}

	protected void asDavid(UnsafeConsumer<ApiClient> action) {
		as("David", action);
	}

	protected void asEve(UnsafeConsumer<ApiClient> action) {
		as("Eve", action);
	}

	protected void asFrank(UnsafeConsumer<ApiClient> action) {
		as("Frank", action);
	}

	protected ApiClient createClient() {
		return new ApiClient("http://localhost:" + port);
	}

	protected void assertStatus(int statusCode, Runnable action) {
		ClientResponseException ex = assertThrows(ClientResponseException.class, () -> action.run());
		assertEquals(statusCode, ex.getStatus(),
				() -> "Unexpected status code: " + ex.getStatus() + ", expected " + statusCode);
	}

	protected static @NotNull UUID extractImageID(String imageUrl) {
		UUID imageId = UUID.fromString(imageUrl.substring(imageUrl.lastIndexOf('/') + 1));
		return imageId;
	}
}
