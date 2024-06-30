package blog.cirkle.api.rest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import blog.cirkle.AbstractApiTest;
import blog.cirkle.api.rest.client.model.PaginatedResponse;
import blog.cirkle.domain.model.UserDto;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class UsersTest extends AbstractApiTest {

	@Test
	void shouldListFewUsers() {
		asAlice(ctx -> {
			PaginatedResponse<UserDto> users = ctx.users().list(0, 3);
			assertThat(users.getContent().size()).isEqualTo(3);
		});
	}

	@Test
	void shouldListAllUsers() {
		asAlice(ctx -> {
			PaginatedResponse<UserDto> users = ctx.users().list();
			assertThat(users.getContent().size()).isEqualTo(10);
		});
	}

	@Test
	void shouldFindById() {
		asAlice(ctx -> {
			Optional<UserDto> alice = ctx.users().byId(ctx.getId());
			assertThat(alice.isPresent()).isTrue();
			UserDto alsoAlice = ctx.getCurrentUser();
			assertThat(alice.get()).isEqualTo(alsoAlice);
		});
	}

	@Test
	void shouldFindBySlug() {
		asAlice(ctx -> {
			UserDto alice = ctx.getCurrentUser();
			Optional<UserDto> alsoAlice = ctx.users().bySlug(alice.getSlug());
			assertThat(alsoAlice.get()).isEqualTo(alice);
		});
	}
}
