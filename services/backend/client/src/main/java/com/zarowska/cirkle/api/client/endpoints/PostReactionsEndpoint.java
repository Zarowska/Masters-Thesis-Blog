package com.zarowska.cirkle.api.client.endpoints;

import com.zarowska.cirkle.api.client.RestTemplateWrapper;
import com.zarowska.cirkle.api.model.CreateReactionRequest;
import com.zarowska.cirkle.api.model.Reaction;
import com.zarowska.cirkle.api.model.ReactionList;
import java.util.Optional;

public class PostReactionsEndpoint extends AbstractClientEndpoint {

	public PostReactionsEndpoint(RestTemplateWrapper restTemplateWrapper) {
		super(restTemplateWrapper);
	}

	public Optional<Reaction> addPostReaction(String userId, String postId,
			CreateReactionRequest createReactionRequest) {
		return null;
	}

	public Optional<Void> deletePostReactionById(String userId, String postId, String reactionId) {
		return null;
	}

	public Optional<Reaction> getPostReactionById(String userId, String postId, String reactionId) {
		return null;
	}

	public Optional<ReactionList> getPostReactions(String userId, String postId) {
		return null;
	}
}
