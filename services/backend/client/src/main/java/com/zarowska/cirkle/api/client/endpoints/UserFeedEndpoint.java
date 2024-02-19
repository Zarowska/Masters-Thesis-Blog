package com.zarowska.cirkle.api.client.endpoints;

import com.zarowska.cirkle.api.client.RestTemplateWrapper;
import com.zarowska.cirkle.api.model.PostsPage;
import java.util.Optional;

public class UserFeedEndpoint extends AbstractClientEndpoint {

	public UserFeedEndpoint(RestTemplateWrapper restTemplateWrapper) {
		super(restTemplateWrapper);
	}

	public Optional<PostsPage> getFeed() {
		return doCall(() -> restTemplateWrapper.get(PostsPage.class, "/user/feed"));
	}
}
