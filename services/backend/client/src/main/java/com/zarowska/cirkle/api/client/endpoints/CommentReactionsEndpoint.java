package com.zarowska.cirkle.api.client.endpoints;

import com.zarowska.cirkle.api.client.RestTemplateWrapper;
import com.zarowska.cirkle.api.model.CreateReactionRequest;
import com.zarowska.cirkle.api.model.Reaction;
import com.zarowska.cirkle.api.model.ReactionList;
import java.util.Optional;
import java.util.UUID;

public class CommentReactionsEndpoint extends AbstractClientEndpoint {

	public CommentReactionsEndpoint(RestTemplateWrapper restTemplateWrapper) {
		super(restTemplateWrapper);
	}

	public Optional<Reaction> addCommentReaction(UUID userId, UUID postId, UUID commentId,
			CreateReactionRequest createReactionRequest) {
		return doCall(() -> restTemplateWrapper.post(createReactionRequest, Reaction.class,
				"/users/{userId}/posts/{postId}/comments/{commentId}/reactions", userId, postId, commentId));
	}

	public Optional<Void> deleteCommentReactionById(UUID userId, UUID postId, UUID commentId, String reactionId) {
		return doCall(() -> restTemplateWrapper.delete(Void.class,
				"/users/{userId}/posts/{postId}/comments/{commentId}/reactions", userId, postId, commentId));
	}

	public Optional<Reaction> getCommentReactionById(UUID userId, UUID postId, UUID commentId, String reactionId) {
		return doCall(() -> restTemplateWrapper.get(Reaction.class,
				"/users/{userId}/posts/{postId}/comments/{commentId}/reactions/{reactionId}", userId, postId, commentId,
				reactionId));
	}

	public Optional<ReactionList> listCommentReactionsById(UUID userId, UUID postId, UUID commentId) {
		return doCall(() -> restTemplateWrapper.get(ReactionList.class,
				"/users/{userId}/posts/{postId}/comments/{commentId}/reactions", userId, postId, commentId));
	}
}
