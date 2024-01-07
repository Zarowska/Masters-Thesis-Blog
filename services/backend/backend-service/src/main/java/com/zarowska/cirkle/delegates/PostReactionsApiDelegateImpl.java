package com.zarowska.cirkle.delegates;

import com.zarowska.cirkle.api.model.CreateReactionRequest;
import com.zarowska.cirkle.api.model.Reaction;
import com.zarowska.cirkle.api.model.ReactionList;
import com.zarowska.cirkle.api.rest.PostReactionsApiDelegate;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostReactionsApiDelegateImpl implements PostReactionsApiDelegate {

	@Override
	public ResponseEntity<Reaction> addPostReaction(UUID userId, UUID postId,
			CreateReactionRequest createReactionRequest) {
		return PostReactionsApiDelegate.super.addPostReaction(userId, postId, createReactionRequest);
	}

	@Override
	public ResponseEntity<Void> deletePostReactionById(UUID userId, UUID postId, UUID reactionId) {
		return PostReactionsApiDelegate.super.deletePostReactionById(userId, postId, reactionId);
	}

	@Override
	public ResponseEntity<Reaction> getPostReactionById(UUID userId, UUID postId, UUID reactionId) {
		return PostReactionsApiDelegate.super.getPostReactionById(userId, postId, reactionId);
	}

	@Override
	public ResponseEntity<ReactionList> getPostReactions(UUID userId, UUID postId) {
		return PostReactionsApiDelegate.super.getPostReactions(userId, postId);
	}
}
