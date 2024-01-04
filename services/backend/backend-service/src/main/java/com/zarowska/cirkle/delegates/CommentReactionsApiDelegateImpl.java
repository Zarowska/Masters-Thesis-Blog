package com.zarowska.cirkle.delegates;

import com.zarowska.cirkle.api.model.CreateReactionRequest;
import com.zarowska.cirkle.api.model.Reaction;
import com.zarowska.cirkle.api.model.ReactionList;
import com.zarowska.cirkle.api.rest.CommentReactionsApiDelegate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentReactionsApiDelegateImpl implements CommentReactionsApiDelegate {

	@Override
	public ResponseEntity<Reaction> addCommentReaction(String userId, String postId, String commentId,
			CreateReactionRequest createReactionRequest) {
		return CommentReactionsApiDelegate.super.addCommentReaction(userId, postId, commentId, createReactionRequest);
	}

	@Override
	public ResponseEntity<Void> deleteCommentReactionById(String userId, String postId, String commentId,
			String reactionId) {
		return CommentReactionsApiDelegate.super.deleteCommentReactionById(userId, postId, commentId, reactionId);
	}

	@Override
	public ResponseEntity<Reaction> getCommentReactionById(String userId, String postId, String commentId,
			String reactionId) {
		return CommentReactionsApiDelegate.super.getCommentReactionById(userId, postId, commentId, reactionId);
	}

	@Override
	public ResponseEntity<ReactionList> listCommentReactionsById(String userId, String postId, String commentId) {
		return CommentReactionsApiDelegate.super.listCommentReactionsById(userId, postId, commentId);
	}
}
