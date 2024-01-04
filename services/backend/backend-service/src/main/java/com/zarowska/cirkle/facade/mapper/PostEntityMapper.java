package com.zarowska.cirkle.facade.mapper;

import com.zarowska.cirkle.api.model.Post;
import com.zarowska.cirkle.domain.entity.PostEntity;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostEntityMapper {
	private final ZoneOffset currentZoneOffset = OffsetDateTime.now().getOffset();

	private final UserEntityMapper userEntityMapper;

	public Post toDto(PostEntity post) {
		return new Post(post.getId(), post.getText(), post.getCreatedAt().atOffset(currentZoneOffset),
				post.getUpdatedAt().atOffset(currentZoneOffset), userEntityMapper.toDto(post.getAuthor()),
				Collections.emptyList());
	}
}
