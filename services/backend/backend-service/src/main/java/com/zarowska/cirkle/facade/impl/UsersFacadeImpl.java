package com.zarowska.cirkle.facade.impl;

import com.zarowska.cirkle.api.model.Profile;
import com.zarowska.cirkle.api.model.User;
import com.zarowska.cirkle.api.model.UserPage;
import com.zarowska.cirkle.domain.entity.UserEntity;
import com.zarowska.cirkle.domain.service.UserService;
import com.zarowska.cirkle.exception.BadRequestException;
import com.zarowska.cirkle.exception.ResourceNotFoundException;
import com.zarowska.cirkle.facade.UsersFacade;
import com.zarowska.cirkle.facade.mapper.UserEntityMapper;
import com.zarowska.cirkle.security.SecurityUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersFacadeImpl implements UsersFacade {

	private final UserService userService;
	private final UserEntityMapper userMapper;
	@PersistenceContext
	private final EntityManager entityManager;

	@Override
	public Profile getProfileById(UUID userId) {
		UserEntity user = userService.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", Map.of("id", userId)));
		return userMapper.toDto(user.getProfile());
	}

	@Override
	public UserPage listUsers(PageRequest pageRequest) {
		return userMapper.toDto(userService.findAll(pageRequest));
	}

	@Override
	public User getUserById(UUID userId) {
		UserEntity user = userService.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", Map.of("id", userId)));
		return userMapper.toDto(user);
	}

	@Override
	public Profile updateProfileById(UUID userId, Profile profile) {
		UserEntity user = userService.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", Map.of("id", userId)));
		UserEntity current = entityManager.merge(SecurityUtils.getCurrentUser().getPrincipal());
		if (!current.getId().equals(user.getId())) {
			throw new BadRequestException("You can only update your own profile");
		}
		user.setProfile(userMapper.toEntity(profile));
		return userMapper.toDto(user.getProfile());
	}
}
