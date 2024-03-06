package com.zarowska.cirkle.delegates;

import com.zarowska.cirkle.api.model.FriendshipRequest;
import com.zarowska.cirkle.api.model.FriendshipRequestList;
import com.zarowska.cirkle.api.model.UserPage;
import com.zarowska.cirkle.api.rest.RelationsApiDelegate;
import com.zarowska.cirkle.facade.FriendshipFacade;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RelationsApiDelegateImpl implements RelationsApiDelegate {

	private final FriendshipFacade friendshipFacade;

	@Override
	public ResponseEntity<Void> acceptFriendshipRequestById(UUID requestId) {
		friendshipFacade.acceptFriendshipRequest(requestId);
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<Void> deleteFriendFromUsersFriendsById(UUID userId, UUID friendId) {
		return RelationsApiDelegate.super.deleteFriendFromUsersFriendsById(userId, friendId);
	}

	@Override
	public ResponseEntity<FriendshipRequestList> findAllFriendshipRequests() {
		return ResponseEntity.ok(friendshipFacade.findAllFriendshipRequests());
	}

	@Override
	public ResponseEntity<FriendshipRequest> getFriendshipRequestById(UUID requestId) {
		return ResponseEntity.ok(friendshipFacade.getFriendshipRequestById(requestId));
	}

	@Override
	public ResponseEntity<UserPage> getUsersFriendsById(UUID userId, Integer page, Integer size) {
		Integer pageValue = Optional.ofNullable(page).orElse(0);
		Integer sizeValue = Optional.ofNullable(size).orElse(100);
		return ResponseEntity.ok(friendshipFacade.findAllFriendsByUserId(userId, PageRequest.of(pageValue, sizeValue)));
	}

	@Override
	public ResponseEntity<Void> rejectFriendshipRequestById(UUID requestId) {
		friendshipFacade.deleteFriendshipRequestById(requestId);
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<Void> sendFrindshipRequest(UUID userId) {
		friendshipFacade.sendFriendshipRequest(userId);
		return ResponseEntity.ok().build();
	}
}
