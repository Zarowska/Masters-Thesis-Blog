package com.zarowska.cirkle.domain.service.impl;

import com.zarowska.cirkle.domain.entity.UserEntity;
import com.zarowska.cirkle.domain.repository.UserEntityRepository;
import com.zarowska.cirkle.domain.service.UserEntityService;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserEntityServiceImpl implements UserEntityService {

	private final UserEntityRepository userEntityRepository;

	@Override
	public Optional<UserEntity> findByEmail(String email) {
		return userEntityRepository.findByProfileEmail(email);
	}

	@Override
	public UserEntity save(UserEntity userEntity) {
		return userEntityRepository.save(userEntity);
	}

	@Override
	public Optional<UserEntity> findById(UUID userId) {
		return userEntityRepository.findById(userId);
	}
}
