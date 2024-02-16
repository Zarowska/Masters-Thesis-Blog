package com.zarowska.cirkle.api;

import static org.assertj.core.api.Assertions.assertThat;

import com.zarowska.cirkle.AbstractTest;
import com.zarowska.cirkle.api.model.Profile;
import com.zarowska.cirkle.api.model.User;
import com.zarowska.cirkle.api.model.UserPage;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class UserApiTest extends AbstractTest {

	// done //Current user info -get-current-user getCurrentUser
	// done // List users - List all users -operationId: list-users listUsers
	// Get user by id - operationId: get-user-by-id getUserById
	// done //get-users-profile-by-id getUsersProfileById
	@Test
	void shouldGetCurrentUser() throws Exception {
		context("Test User", "test@email.com", "http://some/path").apply(ctx -> {
			User actual = ctx.getApi().user().getCurrentUser();
			User expected = new User(null, "Test User", URI.create("http://some/path"));

			assertThat(actual).usingRecursiveComparison().ignoringFields("id").isEqualTo(expected);
		});
	}

	@Test
	void shouldListAllUsers() throws Exception {
		context("Bob Marley", "bob@email", "http://avatar")
				.apply(bobContest -> context("Max Payne", "max@email", "http://avatar2").apply(maxContext -> {
					// max should call api at least once to be created
					maxContext.getApi().users().listUsers();

					Optional<UserPage> allUsers = bobContest.getApi().users().listUsers();

					assertThat(allUsers).isNotEmpty();

					List<String> expected = List.of("Bob Marley", "Max Payne");
					List<String> actual = allUsers.get().getContent().stream().map(User::getName).toList();

					assertThat(actual).containsAll(expected);
				}));

	}

	@Test
	void shouldGetCurrentUserProfile() throws Exception {
		context("Test User", "test@email.com", "http://some/path").apply(ctx -> {
			Profile expected = new Profile("Test User", "test@email.com", URI.create("http://some/path"));
			Optional<Profile> actual = ctx.getApi().users().getUsersProfileById(ctx.getUserId());
			assertThat(actual).isNotEmpty();
			assertThat(expected).isEqualTo(actual.get());
		});
	}

	@Test
	void shouldNotGetCurrentUserProfile() throws Exception {
		context("Test User", "test@email.com", "http://some/path")
				.apply(ctx -> assertThat(ctx.getApi().users().getUsersProfileById(UUID.randomUUID())).isEmpty());
	}

	@Test
	void shouldUpdateProfile() throws Exception {
		context("Test User", "test@email.com", "http://some/path")
				.apply(ctx -> assertThat(ctx.getApi().users().getUsersProfileById(UUID.randomUUID())).isEmpty());
	}
}
