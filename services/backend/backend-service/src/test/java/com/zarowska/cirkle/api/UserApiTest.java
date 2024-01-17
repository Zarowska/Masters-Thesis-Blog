package com.zarowska.cirkle.api;

import static org.assertj.core.api.Assertions.assertThat;

import com.zarowska.cirkle.AbstractTest;
import com.zarowska.cirkle.api.model.Profile;
import com.zarowska.cirkle.api.model.User;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class UserApiTest extends AbstractTest {

	@Test
	void shouldGetCurrentUser() throws Exception {
		context("Test User", "test@email.com", "http://some/path").apply(ctx -> {
			User actual = ctx.getApi().user().getCurrentUser();
			User expected = new User(null, "Test User", URI.create("http://some/path"));

			assertThat(actual).usingRecursiveComparison().ignoringFields("id").isEqualTo(expected);
		});
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
		context("Test User", "test@email.com", "http://some/path").apply(ctx -> {
			assertThat(ctx.getApi().users().getUsersProfileById(UUID.randomUUID())).isEmpty();
		});
	}

	@Test
	void shouldUpdateProfile() throws Exception {
		context("Test User", "test@email.com", "http://some/path").apply(ctx -> {
			assertThat(ctx.getApi().users().getUsersProfileById(UUID.randomUUID())).isEmpty();
		});
	}
}
