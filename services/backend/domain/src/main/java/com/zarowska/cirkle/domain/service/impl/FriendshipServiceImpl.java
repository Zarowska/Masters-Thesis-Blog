package com.zarowska.cirkle.domain.service.impl;

import com.zarowska.cirkle.domain.entity.FriendshipEntity;
import com.zarowska.cirkle.domain.entity.FriendshipRequestEntity;
import com.zarowska.cirkle.domain.entity.UserEntity;
import com.zarowska.cirkle.domain.repository.UserFriendshipEntityRepository;
import com.zarowska.cirkle.domain.repository.UserFriendshipRequestEntityRepository;
import com.zarowska.cirkle.domain.service.FriendshipService;
import com.zarowska.cirkle.exception.ResourceNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendshipServiceImpl implements FriendshipService {

	private final UserFriendshipEntityRepository userFriendshipEntityRepository;
	private final UserFriendshipRequestEntityRepository friendshipRequestEntityRepository;

	@Override
	public void deleteById(UUID id) {
		userFriendshipEntityRepository.deleteById(id);
	}

	@Override
	public List<FriendshipEntity> getUserFriends(UUID userId) {
		return userFriendshipEntityRepository.getUserFriends(userId);
	}

	@Override
	public FriendshipEntity getUserFriendshipWith(UUID currentUserId, UUID userId) {
		return userFriendshipEntityRepository.getUserFriendshipWith(currentUserId, userId)
				.orElseThrow(() -> new ResourceNotFoundException("Friendship",
						"Between %s and %s not set".formatted(currentUserId, userId)));
	}

	@Override
	public FriendshipEntity save(FriendshipEntity friendshipEntity) {
		return userFriendshipEntityRepository.save(friendshipEntity);
	}

	@Override
	public List<FriendshipRequestEntity> findAllFriendshipRequestsByReceiver(UserEntity receiver) {
		return friendshipRequestEntityRepository.getIncomingRequests(receiver);
	}

	@Override
	public FriendshipRequestEntity saveRequest(FriendshipRequestEntity request) {
		return friendshipRequestEntityRepository.save(request);
	}

	@Override
	public void acceptFriendshipRequest(UserEntity currentUser, UUID requestId) {
		friendshipRequestEntityRepository.findById(requestId).map(req -> {
			if (!req.getReceiver().equals(currentUser)) {
				throw new ResourceNotFoundException("FriendshipRequest",
						Map.of("id", requestId, "receiver", currentUser.getId()));
			}
			userFriendshipEntityRepository.save(new FriendshipEntity(req.getSender(), req.getReceiver()));
			userFriendshipEntityRepository.save(new FriendshipEntity(req.getReceiver(), req.getSender()));
			return "";
		}).orElseThrow(() -> new ResourceNotFoundException("FriendshipRequest",
				Map.of("id", requestId, "receiver", currentUser.getId())));
	}

	@Override
	public Page<FriendshipEntity> findAllFriendsByUserId(UUID userId, PageRequest pageRequest) {
		return userFriendshipEntityRepository.findAllFriends(userId, pageRequest);
	}

	@Override
	public void removeFriendship(FriendshipEntity friendshipEntity) {
		removeIndividualFriendship(friendshipEntity.getSender(), friendshipEntity.getReceiver());
		removeIndividualFriendship(friendshipEntity.getReceiver(), friendshipEntity.getSender());
	}

	private void removeIndividualFriendship(UserEntity sender, UserEntity receiver) {
		userFriendshipEntityRepository.findBySenderAndReceiver(sender, receiver)
				.ifPresent(userFriendshipEntityRepository::delete);
	}
}
