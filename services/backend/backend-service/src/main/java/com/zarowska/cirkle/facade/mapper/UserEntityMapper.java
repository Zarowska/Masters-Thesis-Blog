package com.zarowska.cirkle.facade.mapper;

import com.zarowska.cirkle.api.model.Profile;
import com.zarowska.cirkle.api.model.User;
import com.zarowska.cirkle.api.model.UserPage;
import com.zarowska.cirkle.domain.entity.UserEntity;
import com.zarowska.cirkle.domain.entity.UserProfileEntity;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class UserEntityMapper {

	public User toDto(UserEntity entity) {
		return newUser(entity.getId(), entity.getProfile().getName(), URI.create(entity.getProfile().getAvatarUrl()));
	}

	public Profile toDto(UserProfileEntity entity) {
		Profile profile = new Profile();
		profile.setName(entity.getName());
		profile.setEmail(entity.getEmail());
		profile.setAvatarUrl(URI.create(entity.getAvatarUrl()));
		return profile;
	}

	public User newUser(UUID id, String name, URI avatarUrl) {
		User user = new User();
		user.setId(id);
		user.setName(name);
		user.setAvatarUrl(avatarUrl);
		return user;
	}

	public User newUser(String id, String name, String avatarUrl) {
		User user = new User();
		user.setId(UUID.fromString(id));
		user.setName(name);
		user.setAvatarUrl(URI.create(avatarUrl));
		return user;
	}

	public UserPage toDto(Page<UserEntity> page) {
		List<UserEntity> users = page.getContent().stream().toList();
		return UserPage.builder().first(page.isFirst()).empty(page.isEmpty()).size(page.getSize()).last(page.isLast())
				.number(page.getNumber()).numberOfElements(page.getNumberOfElements())
				.content(users.stream().map(this::toDto).toList()).build();
	}

}
