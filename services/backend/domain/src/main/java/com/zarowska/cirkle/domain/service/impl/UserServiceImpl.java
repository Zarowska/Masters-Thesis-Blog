package com.zarowska.cirkle.domain.service.impl;

import com.zarowska.cirkle.domain.entity.UserEntity;
import com.zarowska.cirkle.domain.repository.UserEntityRepository;
import com.zarowska.cirkle.domain.service.UserService;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserEntityRepository userEntityRepository;

	@Override
	public Optional<UserEntity> findById(UUID userId) {
		return userEntityRepository.findById(userId);
	}
}
