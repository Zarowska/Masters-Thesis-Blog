package com.zarowska.cirkle.facade;

import com.zarowska.cirkle.api.model.FriendshipRequest;
import com.zarowska.cirkle.api.model.FriendshipRequestList;
import com.zarowska.cirkle.api.model.UserPage;
import java.util.UUID;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

public interface FriendshipFacade {
	@Transactional
	void sendFriendshipRequest(UUID userId);

	@Transactional(readOnly = true)
	FriendshipRequestList findAllFriendshipRequests();

	@Transactional
	void acceptFriendshipRequest(UUID requestId);

	@Transactional(readOnly = true)
	UserPage findAllFriendsByUserId(UUID userId, PageRequest pageRequest);

	@Transactional
	FriendshipRequest getFriendshipRequestById(UUID requestId);
	@Transactional
	void deleteFriendshipRequestById(UUID requestId);
	@Transactional
	void deleteFriendFromUsersFriendsById(UUID userId, UUID friendId);
}
