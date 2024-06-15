package blog.cirkle.domain.facade.impl;

import blog.cirkle.domain.entity.participant.User;
import blog.cirkle.domain.exception.ResourceNotFoundException;
import blog.cirkle.domain.facade.UserFacade;
import blog.cirkle.domain.facade.mappers.UserMapper;
import blog.cirkle.domain.model.RegistrationResponse;
import blog.cirkle.domain.model.newModel.RelationDto;
import blog.cirkle.domain.model.newModel.RelationRequestDto;
import blog.cirkle.domain.model.newModel.RelationType;
import blog.cirkle.domain.model.newModel.UserDto;
import blog.cirkle.domain.model.request.RegistrationRequest;
import blog.cirkle.domain.model.request.UpdateUserRequest;
import blog.cirkle.domain.model.response.RegistrationResponseDto;
import blog.cirkle.domain.security.UserContextHolder;
import blog.cirkle.domain.service.RelationService;
import blog.cirkle.domain.service.UserService;
import blog.cirkle.domain.service.impl.UserServiceHolder;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFacadeImpl implements UserFacade {
	private final UserService userService;
	private final RelationService relationService;
	private final UserMapper userMapper;

	@Override
	public Page<UserDto> findAll(Pageable pageable) {
		return userService.findAll(pageable).map(userMapper::toUserDto);
	}

	@Override
	public UserDto findBySlug(String slug) {
		return userService.findBySlug(slug).map(userMapper::toUserDto)
				.orElseThrow(() -> new ResourceNotFoundException("User", Map.of("slug", slug)));
	}

	@Override
	public UserDto findById(UUID id) {
		return userService.findById(id).map(userMapper::toUserDto)
				.orElseThrow(() -> new ResourceNotFoundException("User", Map.of("id", id)));
	}

	@Override
	public UserDto getCurrentUser() {
		return findById(UserContextHolder.getCurrentUser().get().getId());
	}

	@Override
	public UserDto updateById(UUID id, UpdateUserRequest request) {
		return userService.updateUser(id, request).map(userMapper::toUserDto)
				.orElseThrow(() -> new ResourceNotFoundException("User", Map.of("id", id)));
	}

	@Override
	public RegistrationResponseDto register(RegistrationRequest request) {
		RegistrationResponse response = userService.register(request);
		return new RegistrationResponseDto(userMapper.toUserDto(response.getUser()), response.getEmailUrl());
	}

	@Override
	public Page<RelationDto> findRelationsByType(UUID userId, Set<RelationType> filter, Pageable pageable) {
		return relationService.findRelationsByType(userId, filter, pageable).map(userMapper::toRelationDto);
	}

	@Override
	public void createRelation(UUID userId, RelationType relationType) {
		userService.findById(userId).map(target -> {
			User initiator = UserServiceHolder.currentUserOrNull();
			relationService.createRelationRequest(initiator, target, relationType);
			return "";
		}).orElseThrow(() -> new ResourceNotFoundException("User", Map.of("id", userId)));
	}

	@Override
	public void removeRelation(UUID userId, RelationType relationType) {
		userService.findById(userId).map(target -> {
			User initiator = UserServiceHolder.currentUserOrNull();
			relationService.removeRelation(initiator, target, relationType);
			return "";
		}).orElseThrow(() -> new ResourceNotFoundException("User", Map.of("id", userId)));
	}

	@Override
	public Page<RelationRequestDto> listUserRelationRequests(Pageable pageable) {
		return relationService.listRelationRequests(UserContextHolder.getCurrentUserOrThrow().getId(),
				Set.of(RelationType.FRIEND), pageable).map(userMapper::toRelationRequestDto);
	}

	@Override
	public void acceptUserRelationRequest(UUID requestId) {
		relationService.acceptRequest(UserContextHolder.getCurrentUserOrThrow().getId(), requestId);
	}

	@Override
	public void rejectUserRelationRequest(UUID requestId) {
		relationService.rejectRequest(UserContextHolder.getCurrentUserOrThrow().getId(), requestId);
	}

}
