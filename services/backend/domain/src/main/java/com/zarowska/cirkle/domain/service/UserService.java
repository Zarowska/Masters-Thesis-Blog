package com.zarowska.cirkle.domain.service;

import com.zarowska.cirkle.domain.entity.UserEntity;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
	Optional<UserEntity> findById(UUID userId);
}
