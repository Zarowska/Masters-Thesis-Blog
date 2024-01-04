package com.zarowska.cirkle.api.client.endpoints;

import com.zarowska.cirkle.api.client.RestTemplateWrapper;
import com.zarowska.cirkle.api.model.FriendshipRequest;
import com.zarowska.cirkle.api.model.FriendshipRequestList;
import com.zarowska.cirkle.api.model.UserPage;

import java.util.Optional;

public class RelationsEndpoint extends AbstractClientEndpoint {

    public RelationsEndpoint(RestTemplateWrapper restTemplateWrapper) {
        super(restTemplateWrapper);
    }

    public Optional<Void> acceptFriendshipRequestById(String requestId) {
        return null;
    }

    public Optional<Void> deleteFriendFromUsersFriendsById(String userId, String friendId) {
        return null;
    }

    public FriendshipRequestList findAllFriendshipRequests() {
        return null;
    }

    public Optional<FriendshipRequest> getFriendshipRequestById(String requestId) {
        return null;
    }

    public Optional<UserPage> getUsersFriendsById(String userId, Integer page, Integer size) {
        return null;
    }

    public Optional<Void> rejectFriendshipRequestById(String requestId) {
        return null;
    }

    public Optional<Void> sendFriendshipRequest(String userId) {
        return doCall(() -> restTemplateWrapper.post(null, Void.class, "/users/{userId}/friends", userId));
    }
}
