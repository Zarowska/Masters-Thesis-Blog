package com.zarowska.cirkle.api;

import static org.assertj.core.api.Assertions.assertThat;

import com.zarowska.cirkle.AbstractTest;
import com.zarowska.cirkle.api.model.Profile;
import com.zarowska.cirkle.api.model.User;
import com.zarowska.cirkle.api.model.UserPage;
import com.zarowska.cirkle.utils.TestUserContext;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserApiTest extends AbstractTest {
	TestUserContext testUserContext;

	@BeforeEach
	void setUp() {
		testUserContext = context("Test User", "test@email.com", "http://some/path");
		testUserContext.getApi().api().apiInfo(); // Simulating API call to setup user
	}

	@Test
	void shouldGetCurrentUser() {
		User actual = testUserContext.getApi().user().getCurrentUser();
		User expected = new User(null, "Test User", URI.create("http://some/path"));
		assertThat(actual).usingRecursiveComparison().ignoringFields("id").isEqualTo(expected);
	}

	@Test
	void shouldGetUserById() {
		Optional<User> userMax = testUserContext.getApi().users()
				.getUserById(testUserContext.getApi().user().getCurrentUser().getId());
		assertThat(userMax).isNotEmpty();
		assertThat(userMax.get().getName()).isEqualTo("Test User");
	}

	@Test
	void shouldListAllUsers() {
		TestUserContext maxContext = context("Bob Marley", "bob@email", "http://avatar");
		TestUserContext bobContext = context("Max Payne", "max@email", "http://avatar2");
		maxContext.getApi().api().apiInfo();
		bobContext.getApi().api().apiInfo();

		Optional<UserPage> allUsers = bobContext.getApi().users().listUsers();

		assertThat(allUsers).isNotEmpty();

		List<String> expected = List.of("Bob Marley", "Max Payne", "Test User");
		List<String> actual = allUsers.get().getContent().stream().map(User::getName).toList();

		assertThat(actual).containsAll(expected);
	}

	@Test
	void shouldGetCurrentUserProfile() {
		Profile expected = new Profile("Test User", "test@email.com", URI.create("http://some/path"));
		Optional<Profile> actual = testUserContext.getApi().users().getUsersProfileById(testUserContext.getUserId());
		assertThat(actual).isNotEmpty();
		assertThat(expected).isEqualTo(actual.get());
	}

	@Test
	void shouldNotGetCurrentUserProfile() {
		assertThat(testUserContext.getApi().users().getUsersProfileById(UUID.randomUUID())).isEmpty();
	}

	// TODO
	@Test
	void shouldUpdateProfile() {
		assertThat(testUserContext.getApi().users().getUsersProfileById(UUID.randomUUID())).isEmpty();
	}
}