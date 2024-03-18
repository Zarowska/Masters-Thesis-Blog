package com.zarowska.cirkle.domain.service;

import com.zarowska.cirkle.domain.entity.FriendshipEntity;
import com.zarowska.cirkle.domain.entity.FriendshipRequestEntity;
import com.zarowska.cirkle.domain.entity.UserEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface FriendshipService {
	void deleteById(UUID id);

	List<FriendshipEntity> getUserFriends(UUID currentUserId);

	FriendshipEntity getUserFriendshipWith(UUID currentUserId, UUID userId);

	FriendshipEntity save(FriendshipEntity friendshipEntity);

	List<FriendshipRequestEntity> findAllFriendshipRequestsByReceiver(UserEntity receiver);

	FriendshipRequestEntity saveRequest(FriendshipRequestEntity request);

	void acceptFriendshipRequest(UserEntity currentUser, UUID requestId);

	Page<FriendshipEntity> findAllFriendsByUserId(UUID userId, PageRequest pageRequest);

	void removeFriendship(FriendshipEntity friendshipEntity);
}
