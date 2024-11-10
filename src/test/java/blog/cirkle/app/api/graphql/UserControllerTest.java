package blog.cirkle.app.api.graphql;

import static org.junit.Assert.assertEquals;

import blog.cirkle.app.api.graphql.model.user.User;
import blog.cirkle.app.api.graphql.model.user.UserPage;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class UserControllerTest extends AbstractGraphQlApiTest {

	@Test
	void shouldGetInformationAboutCurrentUser() {
		asAlice(alice -> {
			User user = alice.user().getCurrentUserInfo();
			assertEquals("Alice", user.getName());
		});
	}

	@Test
	void shouldGetInformationAboutUserById() {
		asAlice(alice -> {
			User user = alice.user().getCurrentUserInfo();
			User byId = alice.user().getUser(user.getId());
			assertEquals(user, byId);
		});
	}

	@Test
	void shouldListUsers() {
		asAlice(alice -> {
			asBob(bob -> {
				asEve(eve -> {
					UserPage userPage = eve.user().listUsers("", 0, 100);
					Set<String> expected = Set.of("Bob", "Eve", "Alice", "admin");
					Set<String> actual = userPage.getContent().stream().map(User::getName).collect(Collectors.toSet());
					assertEquals(expected, actual);
				});
			});
		});
	}
}