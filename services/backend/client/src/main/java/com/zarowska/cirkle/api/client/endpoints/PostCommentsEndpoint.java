package com.zarowska.cirkle.api.client.endpoints;

import com.zarowska.cirkle.api.client.RestTemplateWrapper;
import com.zarowska.cirkle.api.model.Comment;
import com.zarowska.cirkle.api.model.CommentPage;
import com.zarowska.cirkle.api.model.CreateCommentRequest;
import com.zarowska.cirkle.api.model.UpdateCommentRequest;
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
		return null;
	}

	public Optional<Void> deletePostCommentById(UUID userId, UUID postId, UUID commentId) {
		return null;
	}

	public Optional<Comment> getPostCommentById(UUID userId, UUID postId, UUID commentId) {
		return null;
	}

	public Optional<CommentPage> listPostCommentsById(UUID userId, UUID postId, Integer page, Integer size) {
		return null;
	}

	public Optional<Comment> updatePostCommentById(UUID userId, UUID postId, UUID commentId,
			UpdateCommentRequest updateCommentRequest) {
		return null;
	}
}
