package com.zarowska.cirkle.delegates;

import com.zarowska.cirkle.api.model.Comment;
import com.zarowska.cirkle.api.model.CommentPage;
import com.zarowska.cirkle.api.model.CreateCommentRequest;
import com.zarowska.cirkle.api.model.UpdateCommentRequest;
import com.zarowska.cirkle.api.rest.PostCommentsApiDelegate;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostCommentsApiDelegateImpl implements PostCommentsApiDelegate {

	@Override
	public ResponseEntity<Comment> addPostComment(String userId, String postId,
			CreateCommentRequest createCommentRequest) {
		return PostCommentsApiDelegate.super.addPostComment(userId, postId, createCommentRequest);
	}

	@Override
	public ResponseEntity<Void> deletePostCommentById(String userId, String postId, String commentId) {
		return PostCommentsApiDelegate.super.deletePostCommentById(userId, postId, commentId);
	}

	@Override
	public ResponseEntity<Comment> getPostCommentById(String userId, String postId, String commentId) {
		return PostCommentsApiDelegate.super.getPostCommentById(userId, postId, commentId);
	}

	@Override
	public ResponseEntity<CommentPage> listPostCommentsById(String userId, String postId, Integer page,
			Integer size) {
		return PostCommentsApiDelegate.super.listPostCommentsById(userId, postId, page, size);
	}

	@Override
	public ResponseEntity<Comment> updatePostCommentById(String userId, String postId, String commentId,
			UpdateCommentRequest updateCommentRequest) {
		return PostCommentsApiDelegate.super.updatePostCommentById(userId, postId, commentId, updateCommentRequest);
	}
}
