package com.zarowska.cirkle.domain.service;

import com.zarowska.cirkle.domain.entity.UserEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface UserService {
	Optional<UserEntity> findById(UUID userId);

	Page<UserEntity> findAll(PageRequest pageRequest);

}
