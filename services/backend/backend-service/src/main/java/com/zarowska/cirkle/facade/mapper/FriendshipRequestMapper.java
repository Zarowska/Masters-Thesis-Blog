package com.zarowska.cirkle.facade.mapper;

import com.zarowska.cirkle.api.model.FriendshipRequest;
import com.zarowska.cirkle.api.model.FriendshipRequestList;
import com.zarowska.cirkle.domain.entity.FriendshipRequestEntity;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FriendshipRequestMapper {

	private final ZoneOffset currentZoneOffset = OffsetDateTime.now().getOffset();
	private final UserEntityMapper userEntityMapper;

	public FriendshipRequest toDto(FriendshipRequestEntity entity) {
		return FriendshipRequest.builder().id(entity.getId())
				.createdAt(entity.getCreatedAt().atOffset(currentZoneOffset))
				.owner(userEntityMapper.toDto(entity.getSender())).build();
	}

	public FriendshipRequestList toDto(List<FriendshipRequestEntity> requests) {
		return new FriendshipRequestList(requests.stream().map(this::toDto).toList());
	}
}
