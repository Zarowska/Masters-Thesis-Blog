package com.zarowska.cirkle.domain.service.impl;

import com.zarowska.cirkle.domain.entity.FriendshipRequestEntity;
import com.zarowska.cirkle.domain.entity.UserEntity;
import com.zarowska.cirkle.domain.repository.UserFriendshipRequestEntityRepository;
import com.zarowska.cirkle.domain.service.UserFriendRequestService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFriendRequestServiceImpl implements UserFriendRequestService {

	private final UserFriendshipRequestEntityRepository userFriendshipRequestEntityRepository;

	@Override
	public List<FriendshipRequestEntity> getIncomingRequests(UserEntity userEntity) {
		return userFriendshipRequestEntityRepository.getIncomingRequests(userEntity);
	}

	@Override
	public void deleteById(UUID requestId) {
		userFriendshipRequestEntityRepository.deleteById(requestId);
	}

	@Override
	public FriendshipRequestEntity sendFriendshipRequest(UUID userId) {
		FriendshipRequestEntity friendshipRequest = new FriendshipRequestEntity();
		return userFriendshipRequestEntityRepository.save(friendshipRequest);
	}

	@Override
	public Optional<FriendshipRequestEntity> findById(UUID requestId) {
		return Optional.ofNullable(userFriendshipRequestEntityRepository.getRequestById(requestId));
	}

	@Override
	public void delete(FriendshipRequestEntity friendshipRequestEntity) {
		userFriendshipRequestEntityRepository.delete(friendshipRequestEntity);
	}

}
