package com.zarowska.cirkle.delegates;

import com.zarowska.cirkle.api.model.CreateReactionRequest;
import com.zarowska.cirkle.api.model.Reaction;
import com.zarowska.cirkle.api.model.ReactionList;
import com.zarowska.cirkle.api.rest.CommentReactionsApiDelegate;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentReactionsApiDelegateImpl implements CommentReactionsApiDelegate {

	@Override
	public ResponseEntity<Reaction> addCommentReaction(UUID userId, UUID postId, UUID commentId,
			CreateReactionRequest createReactionRequest) {
		return CommentReactionsApiDelegate.super.addCommentReaction(userId, postId, commentId, createReactionRequest);
	}

	@Override
	public ResponseEntity<Void> deleteCommentReactionById(UUID userId, UUID postId, UUID commentId, UUID reactionId) {
		return CommentReactionsApiDelegate.super.deleteCommentReactionById(userId, postId, commentId, reactionId);
	}

	@Override
	public ResponseEntity<Reaction> getCommentReactionById(UUID userId, UUID postId, UUID commentId, UUID reactionId) {
		return CommentReactionsApiDelegate.super.getCommentReactionById(userId, postId, commentId, reactionId);
	}

	@Override
	public ResponseEntity<ReactionList> listCommentReactionsById(UUID userId, UUID postId, UUID commentId) {
		return CommentReactionsApiDelegate.super.listCommentReactionsById(userId, postId, commentId);
	}
}
