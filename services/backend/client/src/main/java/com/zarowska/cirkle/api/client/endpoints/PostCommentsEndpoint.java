package com.zarowska.cirkle.api.client.endpoints;

import com.zarowska.cirkle.api.client.RestTemplateWrapper;
import com.zarowska.cirkle.api.model.Comment;
import com.zarowska.cirkle.api.model.CommentPage;
import com.zarowska.cirkle.api.model.CreateCommentRequest;
import com.zarowska.cirkle.api.model.UpdateCommentRequest;

import java.util.Optional;

public class PostCommentsEndpoint extends AbstractClientEndpoint {

	public PostCommentsEndpoint(RestTemplateWrapper restTemplateWrapper) {
		super(restTemplateWrapper);
	}

	public CommentReactionsEndpoint commentReactions() {
		return new CommentReactionsEndpoint(restTemplateWrapper);
	}

	public Optional<Comment> addPostComment(String userId, String postId, CreateCommentRequest createCommentRequest) {
		return null;
	}

	public Optional<Void> deletePostCommentById(String userId, String postId, String commentId) {
		return null;
	}

	public Optional<Comment> getPostCommentById(String userId, String postId, String commentId) {
		return null;
	}

	public Optional<CommentPage> listPostCommentsById(String userId, String postId, Integer page, Integer size) {
		return null;
	}

	public Optional<Comment> updatePostCommentById(String userId, String postId, String commentId,
			UpdateCommentRequest updateCommentRequest) {
		return null;
	}
}
