package com.zarowska.cirkle.delegates;

import com.zarowska.cirkle.api.model.CreateReactionRequest;
import com.zarowska.cirkle.api.model.Reaction;
import com.zarowska.cirkle.api.model.ReactionList;
import com.zarowska.cirkle.api.rest.PostReactionsApiDelegate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostReactionsApiDelegateImpl implements PostReactionsApiDelegate {

	@Override
	public ResponseEntity<Reaction> addPostReaction(String userId, String postId,
			CreateReactionRequest createReactionRequest) {
		return PostReactionsApiDelegate.super.addPostReaction(userId, postId, createReactionRequest);
	}

	@Override
	public ResponseEntity<Void> deletePostReactionById(String userId, String postId, String reactionId) {
		return PostReactionsApiDelegate.super.deletePostReactionById(userId, postId, reactionId);
	}

	@Override
	public ResponseEntity<Reaction> getPostReactionById(String userId, String postId, String reactionId) {
		return PostReactionsApiDelegate.super.getPostReactionById(userId, postId, reactionId);
	}

	@Override
	public ResponseEntity<ReactionList> getPostReactions(String userId, String postId) {
		return PostReactionsApiDelegate.super.getPostReactions(userId, postId);
	}
}
