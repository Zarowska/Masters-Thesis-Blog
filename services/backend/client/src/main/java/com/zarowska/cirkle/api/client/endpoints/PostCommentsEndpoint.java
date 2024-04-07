package com.zarowska.cirkle.api.client.endpoints;

import com.zarowska.cirkle.api.client.RestTemplateWrapper;
import com.zarowska.cirkle.api.model.*;
import java.util.Optional;
import java.util.UUID;

public class PostCommentsEndpoint extends AbstractClientEndpoint {

	public PostCommentsEndpoint(RestTemplateWrapper restTemplateWrapper) {
		super(restTemplateWrapper);
	}

	public CommentReactionsEndpoint commentReactions() {
		return new CommentReactionsEndpoint(restTemplateWrapper);
	}

	public Optional<Comment> addPostComment(UUID userId, UUID postId, CreateCommentRequest createCommentRequest) {
		return doCall(() -> restTemplateWrapper.post(createCommentRequest, Comment.class,
				"/users/{userId}/posts/{postId}/comments", userId, postId));

	}

	public Optional<Void> deletePostCommentById(UUID userId, UUID postId, UUID commentId) {
		return doCall(() -> restTemplateWrapper.delete(Void.class,
				"/users/{userId}/posts/{postId}/comments/{commentId}", userId, postId, commentId));
	}

	public Optional<Comment> getPostCommentById(UUID userId, UUID postId, UUID commentId) {
		return doCall(() -> restTemplateWrapper.get(Comment.class,
				"/users/{userId}/posts/{postId}/comments/{commentId}", userId, postId, commentId));

	}

	public Optional<CommentPage> listPostCommentsById(UUID userId, UUID postId) {
		return doCall(() -> restTemplateWrapper.get(CommentPage.class,
				"/users/{userId}/posts/{postId}/comments/{commentId}", userId, postId));

	}

	public Optional<Comment> updatePostCommentById(UUID userId, UUID postId, UUID commentId,
			UpdateCommentRequest updateCommentRequest) {
		return doCall(() -> restTemplateWrapper.put(updateCommentRequest, Comment.class,
				"/users/{userId}/posts/{postId}/comments", userId, postId, commentId));

	}
}
