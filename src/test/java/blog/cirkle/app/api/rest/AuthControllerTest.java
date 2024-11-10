package blog.cirkle.app.api.rest;

import static org.junit.jupiter.api.Assertions.*;

import blog.cirkle.app.api.rest.client.ApiClient;
import blog.cirkle.app.api.rest.model.AuthenticateResponse;
import blog.cirkle.app.api.rest.model.NewUserDto;
import blog.cirkle.app.api.rest.model.ParticipantDto;
import blog.cirkle.app.api.rest.model.ResetPasswordDto;
import blog.cirkle.app.api.rest.model.request.CreateUserDto;
import org.junit.jupiter.api.Test;

class AuthControllerTest extends AbstractRestApiTest {

	@Test
	void shouldRegister() {
		ApiClient client = createClient();

		NewUserDto preRegistration = client.auth
				.register(CreateUserDto.builder().email("authResponse@cirkle.blog").fullName("Alice").build());
		assertNotNull(preRegistration.getPasswordResetId());
		AuthenticateResponse authResponse = client.auth.resetPassword(preRegistration.getPasswordResetId(),
				new ResetPasswordDto("__alice"));
		assertNotNull(authResponse.getToken());
	}

	@Test
	void shouldRegisterWithClient() {
		ApiClient client = createClient();
		client.register(CreateUserDto.builder().email("authResponse@cirkle.blog").fullName("Alice").build(),
				"__alice_password");

		assertTrue(client.isLoggedIn());
	}

	@Test
	void shouldLoginWithUsernameAndPassword() {
		ApiClient client = createClient();
		client.register(CreateUserDto.builder().email("authResponse@cirkle.blog").fullName("Alice").build(),
				"__alice_password");
		ApiClient client2 = createClient();
		AuthenticateResponse authResponse = client2.auth.authByBasic("authResponse@cirkle.blog", "__alice_password");
		assertNotNull(authResponse.getToken());
	}

	@Test
	void shouldLoginClientWithUsernameAndPassword() {
		ApiClient client = createClient();
		client.register(CreateUserDto.builder().email("authResponse@cirkle.blog").fullName("Alice").build(),
				"__alice_password");
		ApiClient client2 = createClient();
		client2.login("authResponse@cirkle.blog", "__alice_password");
		assertTrue(client.isLoggedIn());
	}

	@Test
	void loggedInClientShouldAccessApi() {
		ApiClient client = createClient();
		assertStatus(403, () -> client.user.getCurrentUserInfo());

		client.register(CreateUserDto.builder().email("authResponse@cirkle.blog").fullName("Alice").build(),
				"__alice_password");

		ParticipantDto currentUserInfo = client.user.getCurrentUserInfo();
		System.out.println(currentUserInfo);

	}

}