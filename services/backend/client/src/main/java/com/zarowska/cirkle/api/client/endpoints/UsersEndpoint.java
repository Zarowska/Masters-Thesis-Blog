package com.zarowska.cirkle.api.client.endpoints;

import com.zarowska.cirkle.api.client.RestTemplateWrapper;
import com.zarowska.cirkle.api.model.Profile;
import com.zarowska.cirkle.api.model.User;
import com.zarowska.cirkle.api.model.UserPage;
import java.util.Optional;
import java.util.UUID;

public class UsersEndpoint extends AbstractClientEndpoint {

	public UsersEndpoint(RestTemplateWrapper restTemplateWrapper) {
		super(restTemplateWrapper);
	}

	public Optional<User> getUserById(UUID userId) {
		return null;
	}

	public Optional<Profile> getUsersProfileById(UUID userId) {
		return doCall(() -> restTemplateWrapper.get(Profile.class, "/users/{userId}/profile", userId));
	}

	public UserPage listUsers() {
		return null;
	}
}
