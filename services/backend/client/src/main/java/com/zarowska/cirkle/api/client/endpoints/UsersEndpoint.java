package com.zarowska.cirkle.api.client.endpoints;

import com.zarowska.cirkle.api.client.RestTemplateWrapper;
import com.zarowska.cirkle.api.model.Profile;
import com.zarowska.cirkle.api.model.User;
import com.zarowska.cirkle.api.model.UserPage;
import java.util.Optional;

public class UsersEndpoint extends AbstractClientEndpoint {

	public UsersEndpoint(RestTemplateWrapper restTemplateWrapper) {
		super(restTemplateWrapper);
	}

	public Optional<User> getUserById(String userId) {
		return null;
	}

	public Optional<Profile> getUsersProfileById(String userId) {
		return doCall(() -> restTemplateWrapper.get(Profile.class, "/users/{userId}/profile", userId));
	}

	public UserPage listUsers() {
		return null;
	}
}
