package blog.cirkle.api.rest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import blog.cirkle.AbstractApiTest;
import blog.cirkle.domain.model.newModel.UserDto;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class UserTest extends AbstractApiTest {

	@Test
	void shouldGetCurrentUser() {
		UserDto expected = UserDto.builder().id(UUID.randomUUID()).firstName("Alice").lastName("Testson").build();
		asAlice(client -> {
			UserDto currentUser = client.getCurrentUser();
			assertThat(currentUser).usingRecursiveComparison().ignoringFields("id", "slug", "avatarUrl")
					.isEqualTo(expected);
		});
	}

	// TODO /feed

}
