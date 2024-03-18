package com.zarowska.cirkle.facade.impl;

import com.zarowska.cirkle.api.model.User;
import com.zarowska.cirkle.domain.entity.UserEntity;
import com.zarowska.cirkle.facade.UserFacade;
import com.zarowska.cirkle.facade.mapper.UserEntityMapper;
import com.zarowska.cirkle.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFacadeImpl implements UserFacade {
	private final UserEntityMapper mapper;

	@Override
	public User getCurrentUser() {
		UserEntity userEntity = SecurityUtils.getCurrentUser().getPrincipal();
		return mapper.toDto(userEntity);
	}

}
