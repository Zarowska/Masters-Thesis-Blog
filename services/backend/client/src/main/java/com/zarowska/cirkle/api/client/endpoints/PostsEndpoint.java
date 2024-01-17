package com.zarowska.cirkle.api.client.endpoints;

import com.zarowska.cirkle.api.client.RestTemplateWrapper;
import com.zarowska.cirkle.api.model.CreatePostRequest;
import com.zarowska.cirkle.api.model.Post;
import com.zarowska.cirkle.api.model.PostsPage;
import com.zarowska.cirkle.api.model.UpdatePostRequest;
import java.util.Optional;
import java.util.UUID;

public class PostsEndpoint extends AbstractClientEndpoint {

	public PostsEndpoint(RestTemplateWrapper restTemplateWrapper) {
		super(restTemplateWrapper);
	}

	public PostCommentsEndpoint comments() {
		return new PostCommentsEndpoint(restTemplateWrapper);
	}

	public PostReactionsEndpoint reactions() {
		return new PostReactionsEndpoint(restTemplateWrapper);
	}

	public Optional<Post> createPost(UUID userId, CreatePostRequest createPostRequest) {
		return doCall(() -> restTemplateWrapper.post(createPostRequest, Post.class, "/users/{userId}/posts", userId));

	}

	public Optional<Void> deleteUserPostById(UUID userId, UUID postId) {
		return null;
	}

	public Optional<Post> getUserPostByPostId(UUID userId, UUID postId) {
		return doCall(() -> restTemplateWrapper.get(Post.class, "/users/{userId}/posts/{postId}", userId, postId));
	}

	public Optional<PostsPage> listUsersPostsByUserId(UUID userId) {
		return doCall(() -> restTemplateWrapper.get(PostsPage.class, "/users/{userId}/posts", userId));
	}

	public Optional<PostsPage> listUsersPostsByUserId(UUID userId, Integer page, Integer size) {
		return null;
	}

	public Optional<Post> updateUserPostById(UUID userId, UUID postId, UpdatePostRequest updatePostRequest) {
		return doCall(() -> restTemplateWrapper.put(updatePostRequest, Post.class, "/users/{userId}/posts/{postId}",
				userId, postId));
	}
}
