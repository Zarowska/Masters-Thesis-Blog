package com.zarowska.cirkle.api.client;

import com.zarowska.cirkle.api.client.endpoints.*;

public class BlogClient {

	private final RestTemplateWrapper restTemplateWrapper;

	public BlogClient(String baseUrl) {
		restTemplateWrapper = new RestTemplateWrapper(baseUrl);
	}

	public static BlogClient local(int port) {
		return new BlogClient("http://localhost:%d/api/v1".formatted(port));
	}

	public ApiEndpoint api() {
		return new ApiEndpoint(restTemplateWrapper);
	}

	public ImagesEndpoint images() {
		return new ImagesEndpoint(restTemplateWrapper);
	}

	public MessagesEndpoint messages() {
		return new MessagesEndpoint(restTemplateWrapper);
	}

	public PostsEndpoint posts() {
		return new PostsEndpoint(restTemplateWrapper);
	}

	public RelationsEndpoint relations() {
		return new RelationsEndpoint(restTemplateWrapper);
	}

	public UserEndpoint user() {
		return new UserEndpoint(restTemplateWrapper);
	}

	public UserFeedEndpoint userFeed() {
		return new UserFeedEndpoint(restTemplateWrapper);
	}

	public UsersEndpoint users() {
		UsersEndpoint usersEndpoint = new UsersEndpoint(restTemplateWrapper);
		return usersEndpoint;
	}

	public void setBearerToken(String bearerToken) {
		restTemplateWrapper.setBearerToken(bearerToken);

	}

}
