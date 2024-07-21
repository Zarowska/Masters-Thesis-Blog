package blog.cirkle.api.rest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import blog.cirkle.AbstractApiTest;
import blog.cirkle.api.rest.client.ApiClient;
import blog.cirkle.domain.model.UserDto;
import org.junit.jupiter.api.Test;

class RegistrationTest extends AbstractApiTest {

	@Test
	void shouldRegisterAndLogin() {
		ApiClient client = createClient();
		String email = "john.doe@example.com";
		String password = "password123";
		client.registration().register(email, "John", "Doe", password);
		client.auth().login(email, password);
		UserDto currentUser = client.getCurrentUser();
		assertThat(currentUser.getFirstName()).isEqualTo("John");
		assertThat(currentUser.getLastName()).isEqualTo("Doe");
	}
}
