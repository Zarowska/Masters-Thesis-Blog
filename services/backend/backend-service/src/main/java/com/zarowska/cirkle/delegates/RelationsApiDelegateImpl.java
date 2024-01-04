package com.zarowska.cirkle.delegates;

import com.zarowska.cirkle.api.model.FriendshipRequest;
import com.zarowska.cirkle.api.model.FriendshipRequestList;
import com.zarowska.cirkle.api.model.UserPage;
import com.zarowska.cirkle.api.rest.RelationsApiDelegate;

import com.zarowska.cirkle.facade.FriendshipFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RelationsApiDelegateImpl implements RelationsApiDelegate {

    private final FriendshipFacade friendshipFacade;

    @Override
    public ResponseEntity<Void> acceptFriendshipRequestById(String requestId) {
        return RelationsApiDelegate.super.acceptFriendshipRequestById(requestId);
    }

    @Override
    public ResponseEntity<Void> deleteFriendFromUsersFriendsById(String userId, String friendId) {
        return RelationsApiDelegate.super.deleteFriendFromUsersFriendsById(userId, friendId);
    }

    @Override
    public ResponseEntity<FriendshipRequestList> findAllFriendshipRequests() {
        return RelationsApiDelegate.super.findAllFriendshipRequests();
    }

    @Override
    public ResponseEntity<FriendshipRequest> getFriendshipRequestById(String requestId) {
        return RelationsApiDelegate.super.getFriendshipRequestById(requestId);
    }

    @Override
    public ResponseEntity<UserPage> getUsersFriendsById(String userId, Integer page, Integer size) {
        return RelationsApiDelegate.super.getUsersFriendsById(userId, page, size);
    }

    @Override
    public ResponseEntity<Void> rejectFriendshipRequestById(String requestId) {
        return RelationsApiDelegate.super.rejectFriendshipRequestById(requestId);
    }

    @Override
    public ResponseEntity<Void> sendFrindshipRequest(String userId) {
        friendshipFacade.sendFriendshipRequest(userId);
        return ResponseEntity.ok().build();
    }
}
