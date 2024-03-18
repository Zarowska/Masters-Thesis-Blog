package com.zarowska.cirkle.api.client.endpoints;

import com.zarowska.cirkle.api.client.RestTemplateWrapper;
import com.zarowska.cirkle.api.model.CreateReactionRequest;
import com.zarowska.cirkle.api.model.Reaction;
import com.zarowska.cirkle.api.model.ReactionList;
import java.util.Optional;
import java.util.UUID;

public class PostReactionsEndpoint extends AbstractClientEndpoint {

	public PostReactionsEndpoint(RestTemplateWrapper restTemplateWrapper) {
		super(restTemplateWrapper);
	}

	public Optional<Reaction> addPostReaction(UUID userId, UUID postId, CreateReactionRequest createReactionRequest) {
		return null;
	}

	public Optional<Void> deletePostReactionById(UUID userId, UUID postId, String reactionId) {
		return null;
	}

	public Optional<Reaction> getPostReactionById(UUID userId, UUID postId, String reactionId) {
		return null;
	}

	public Optional<ReactionList> getPostReactions(UUID userId, UUID postId) {
		return null;
	}
}
