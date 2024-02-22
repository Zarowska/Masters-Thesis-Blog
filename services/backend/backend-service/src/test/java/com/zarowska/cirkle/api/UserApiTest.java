package com.zarowska.cirkle.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.zarowska.cirkle.AbstractTest;
import com.zarowska.cirkle.api.model.*;
import com.zarowska.cirkle.utils.TestUserContext;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserApiTest extends AbstractTest {

	// done //Api Info //GET ///info
	// done //get Current user info
	// done //User's feed
	// done //List users
	// done //Get user by id
	// done //get-users-profile-by-id
	// done//update-users-profile-by-id

	TestUserContext testUserContext;

	@BeforeEach
	void setUp() {
		testUserContext = context("Test User", "test@email.com", "http://some/path");
		testUserContext.getApi().api().apiInfo(); // Simulating API call to setup user
	}

	@Test
	void shouldGetApiInfo() {
		assertThat(testUserContext.getApi().api().apiInfo().toString()).contains("version:", "buildDate:", "buildNum:",
				"environment:");
	}

	@Test
	void shouldGetFeed() {
		List<String> expectedPostText = List.of("Post1", "Post2", "Post3", "Post4");
		expectedPostText.stream().map(text -> testUserContext.getApi().posts().createPost(testUserContext.getUserId(),
				new CreatePostRequest().text(text))).map(Optional::get).toList();

		Optional<PostsPage> postsPage = testUserContext.getApi().userFeed().getFeed();

		List<String> allPostText = postsPage.get().getContent().stream().map(Post::getText).toList();

		assertThat(allPostText).containsAll(expectedPostText);
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

	@Test
	void shouldUpdateProfile() { // editing data about yourself
		Profile expected = new Profile().name("New User Name").avatarUrl(URI.create("http://some/new_path"))
				.email("new@new.com");
		Profile before = testUserContext.getApi().users().getUsersProfileById(testUserContext.getUserId()).get();
		assertThat(before).isNotEqualTo(expected);
		// update
		testUserContext.getApi().users().updateUsersProfileById(testUserContext.getUserId(), expected);
		Profile after = testUserContext.getApi().users().getUsersProfileById(testUserContext.getUserId()).get();
		assertThat(after).isEqualTo(expected);
	}

	@Test
	void updateProfile_ShouldThrowException() throws Exception {
		User currentUser = testUserContext.getApi().user().getCurrentUser();
		assertThat(currentUser.getName()).isEqualTo("Test User");
		assertThat(currentUser.getAvatarUrl()).isEqualTo(URI.create("http://some/path"));

		// try {
		context("Max Payne", "max@email", "http://avatar2").apply(maxContext -> {
			Profile newProfile = new Profile().name("New User Name").avatarUrl(URI.create("http://some/new_path"))
					.email("new@new.com");
			assertThatThrownBy(
					() -> testUserContext.getApi().users().updateUsersProfileById(maxContext.getUserId(), newProfile))
					.hasMessage("You can only update your own profile");
		});
	}
}
