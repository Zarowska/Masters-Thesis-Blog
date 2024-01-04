package com.zarowska.cirkle.domain.service.impl;

import com.zarowska.cirkle.domain.entity.FriendshipEntity;
import com.zarowska.cirkle.domain.repository.UserFriendshipEntityRepository;
import com.zarowska.cirkle.domain.service.FriendshipService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendshipServiceImpl implements FriendshipService {

	private final UserFriendshipEntityRepository userFriendshipEntityRepository;

	@Override
	public void removeFriend(UUID friendId) {
		userFriendshipEntityRepository.removeFriend(friendId);
	}

	@Override
	public List<FriendshipEntity> getUserFriends(UUID userId) {
		return userFriendshipEntityRepository.getUserFriends(userId);
	}

	@Override
	public FriendshipEntity getUserFriendshipWith(UUID currentUserId, UUID userId) {
		return userFriendshipEntityRepository.getUserFriendshipWith(currentUserId, userId);
	}

	@Override
	public FriendshipEntity save(FriendshipEntity friendshipEntity) {
		return friendshipEntity;
	}

}
