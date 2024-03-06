package com.zarowska.cirkle.api.client.endpoints;

import com.zarowska.cirkle.api.client.RestTemplateWrapper;
import com.zarowska.cirkle.api.model.FriendshipRequest;
import com.zarowska.cirkle.api.model.FriendshipRequestList;
import com.zarowska.cirkle.api.model.UserPage;
import java.util.Optional;
import java.util.UUID;

public class RelationsEndpoint extends AbstractClientEndpoint {

	public RelationsEndpoint(RestTemplateWrapper restTemplateWrapper) {
		super(restTemplateWrapper);
	}

	public Optional<Void> acceptFriendshipRequestById(UUID requestId) {
		return doCall(() -> restTemplateWrapper.post(null, null, "/user/requests/{requestId}", requestId));
	}

	public Optional<Void> deleteFriendFromUsersFriendsById(UUID userId, String friendId) {
		return null;
	}

	public FriendshipRequestList findAllFriendshipRequests() {
		return doCall(() -> restTemplateWrapper.get(FriendshipRequestList.class, "/user/requests")).get();
	}

	public Optional<FriendshipRequest> getFriendshipRequestById(UUID requestId) {
		return doCall(() -> restTemplateWrapper.get(FriendshipRequest.class, "/user/requests/{requestId}", requestId));
	}

	public Optional<UserPage> getUsersFriendsById(UUID userId, Integer page, Integer size) {
		return doCall(() -> restTemplateWrapper.get(UserPage.class, "/users/{userId}/friends", userId));
	}

	public Optional<Void> rejectFriendshipRequestById(UUID requestId) {
		return null;
	}

	public Optional<Void> sendFriendshipRequest(UUID userId) {
		return doCall(() -> restTemplateWrapper.post(null, Void.class, "/users/{userId}/friends", userId));
	}
}
