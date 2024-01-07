package com.zarowska.cirkle.facade.mapper;

import com.zarowska.cirkle.api.model.UserPage;
import com.zarowska.cirkle.domain.entity.FriendshipEntity;
import com.zarowska.cirkle.domain.entity.UserEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FriendshipEntityMapper {

	private final UserEntityMapper userEntityMapper;

	public UserPage toDto(Page<FriendshipEntity> page) {
		List<UserEntity> users = page.getContent().stream().map(FriendshipEntity::getSender).toList();

		return UserPage.builder().first(page.isFirst()).empty(page.isEmpty()).size(page.getSize()).last(page.isLast())
				.number(page.getNumber()).numberOfElements(page.getNumberOfElements())
				.content(users.stream().map(userEntityMapper::toDto).toList()).build();
	}
}
