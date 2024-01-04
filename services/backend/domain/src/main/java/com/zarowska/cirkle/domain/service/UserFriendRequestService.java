package com.zarowska.cirkle.domain.service;

import com.zarowska.cirkle.domain.entity.FriendshipRequestEntity;
import com.zarowska.cirkle.domain.entity.UserEntity;
import java.util.List;
import java.util.UUID;

public interface UserFriendRequestService {
	List<FriendshipRequestEntity> getIncomingRequests(UserEntity userEntity);

	void deleteById(UUID requestId);

	FriendshipRequestEntity sendFriendshipRequest(UUID userId);

	FriendshipRequestEntity getRequestById(UUID requestId);

}
