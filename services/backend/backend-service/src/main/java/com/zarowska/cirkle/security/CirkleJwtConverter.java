package com.zarowska.cirkle.security;

import static com.zarowska.cirkle.utils.JwtUtils.*;

import com.zarowska.cirkle.api.CustomJwtConverter;
import com.zarowska.cirkle.domain.entity.UserEntity;
import com.zarowska.cirkle.domain.entity.UserProfileEntity;
import com.zarowska.cirkle.domain.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CirkleJwtConverter implements CustomJwtConverter {

	private final UserEntityService userEntityService;

	@Override
	public AbstractAuthenticationToken convert(Jwt jwt) {

		UserEntity userEntity = userEntityService.findByEmail(getEmail(jwt))
				.orElseGet(() -> userEntityService.save(toUserEntity(jwt)));

		return new BlogUser(jwt, userEntity);
	}

	private UserEntity toUserEntity(Jwt jwt) {
		return new UserEntity().setProfile(toProfile(jwt));
	}

	private UserProfileEntity toProfile(Jwt jwt) {
		return new UserProfileEntity().setEmail(getEmail(jwt)).setName(getName(jwt)).setAvatarUrl(getAvatarUrl(jwt));
	}
}
