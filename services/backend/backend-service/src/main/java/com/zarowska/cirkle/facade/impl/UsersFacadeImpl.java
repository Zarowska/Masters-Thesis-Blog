package com.zarowska.cirkle.facade.impl;

import com.zarowska.cirkle.api.model.Profile;
import com.zarowska.cirkle.domain.entity.UserEntity;
import com.zarowska.cirkle.domain.service.UserService;
import com.zarowska.cirkle.exception.ResourceNotFoundException;
import com.zarowska.cirkle.facade.UsersFacade;
import com.zarowska.cirkle.facade.mapper.UserEntityMapper;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersFacadeImpl implements UsersFacade {

	private final UserService userService;
	private final UserEntityMapper userMapper;

	@Override
	public Profile getProfileById(UUID userId) {
		UserEntity user = userService.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", Map.of("id", userId)));
		return userMapper.toDto(user.getProfile());
	}
}
