package com.zarowska.cirkle.domain.service;

import com.zarowska.cirkle.domain.entity.FriendshipEntity;
import java.util.List;
import java.util.UUID;

public interface FriendshipService {

	void removeFriend(UUID friendId);

	List<FriendshipEntity> getUserFriends(UUID currentUserId);

	FriendshipEntity getUserFriendshipWith(UUID currentUserId, UUID userId);

	FriendshipEntity save(FriendshipEntity friendshipEntity);
}
