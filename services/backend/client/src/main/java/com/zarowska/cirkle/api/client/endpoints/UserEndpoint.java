package com.zarowska.cirkle.api.client.endpoints;

import com.zarowska.cirkle.api.client.RestTemplateWrapper;
import com.zarowska.cirkle.api.model.User;

public class UserEndpoint extends AbstractClientEndpoint {
	public UserEndpoint(RestTemplateWrapper restTemplateWrapper) {
		super(restTemplateWrapper);
	}

	public User getCurrentUser() {
		return restTemplateWrapper.get(User.class, "/user").getBody();
	}
}
