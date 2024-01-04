package com.zarowska.cirkle.api.client.endpoints;

import com.zarowska.cirkle.api.client.RestTemplateWrapper;
import com.zarowska.cirkle.api.model.PostsPage;

public class UserFeedEndpoint extends AbstractClientEndpoint {

	public UserFeedEndpoint(RestTemplateWrapper restTemplateWrapper) {
		super(restTemplateWrapper);
	}

	public PostsPage getFeed(Integer page, Integer size) {
		return null;
	}
}
