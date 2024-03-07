package com.zarowska.cirkle.facade.impl;

import com.zarowska.cirkle.api.model.FriendshipRequest;
import com.zarowska.cirkle.api.model.FriendshipRequestList;
import com.zarowska.cirkle.api.model.UserPage;
import com.zarowska.cirkle.domain.entity.FriendshipRequestEntity;
import com.zarowska.cirkle.domain.entity.UserEntity;
import com.zarowska.cirkle.domain.service.FriendshipService;
import com.zarowska.cirkle.domain.service.UserEntityService;
import com.zarowska.cirkle.domain.service.UserFriendRequestService;
import com.zarowska.cirkle.exception.BadRequestException;
import com.zarowska.cirkle.exception.ResourceNotFoundException;
import com.zarowska.cirkle.facade.FriendshipFacade;
import com.zarowska.cirkle.facade.mapper.FriendshipEntityMapper;
import com.zarowska.cirkle.facade.mapper.FriendshipRequestMapper;
import com.zarowska.cirkle.security.SecurityUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendshipFacadeImpl implements FriendshipFacade {

	private final UserFriendRequestService userFriendRequestService;
	private final UserEntityService userEntityService;
	private final FriendshipService friendshipService;
	private final FriendshipRequestMapper friendshipRequestMapper;
	private final FriendshipEntityMapper friendshipEntityMapper;

	@PersistenceContext
	private final EntityManager em;

	@Override
	public void sendFriendshipRequest(UUID userId) {
		userEntityService.findById(userId).map(targetUser -> {
			UserEntity currentUser = em.merge(SecurityUtils.getCurrentUser().getPrincipal());
			boolean alreadyFriends = friendshipService.findAllFriendsByUserId(currentUser.getId(), null).stream()
					.anyMatch(it -> it.getSender().getId().equals(targetUser.getId()));
			if (alreadyFriends) {
				throw new BadRequestException("Users are already friends");
			}
			FriendshipRequestEntity request = new FriendshipRequestEntity(currentUser, targetUser);
			return friendshipService.saveRequest(request);
		}).orElseThrow(() -> new ResourceNotFoundException("user", "id=%s".formatted(userId)));
	}

	@Override
	public FriendshipRequestList findAllFriendshipRequests() {
		UserEntity currentUser = em.merge(SecurityUtils.getCurrentUser().getPrincipal());
		List<FriendshipRequestEntity> requests = friendshipService.findAllFriendshipRequestsByReceiver(currentUser);
		return friendshipRequestMapper.toDto(requests);
	}

	@Override
	public void acceptFriendshipRequest(UUID requestId) {
		UserEntity currentUser = em.merge(SecurityUtils.getCurrentUser().getPrincipal());
		friendshipService.acceptFriendshipRequest(currentUser, requestId);
	}

	@Override
	public UserPage findAllFriendsByUserId(UUID userId, PageRequest pageRequest) {
		return friendshipEntityMapper.toDto(friendshipService.findAllFriendsByUserId(userId, pageRequest));
	}

	@Override
	public FriendshipRequest getFriendshipRequestById(UUID requestId) {
		FriendshipRequestEntity friendshipRequestEntity = userFriendRequestService.findById(requestId)
				.orElseThrow(() -> new BadRequestException("Friendship request not found with id=" + requestId));
		return friendshipRequestMapper.toDto(friendshipRequestEntity);
	}

	@Override
	public void deleteFriendshipRequestById(UUID requestId) {
		FriendshipRequestEntity friendshipRequestEntity = userFriendRequestService.findById(requestId)
				.orElseThrow(() -> new BadRequestException("Friendship request not found with id=" + requestId));
		userFriendRequestService.delete(friendshipRequestEntity);
	}

}
