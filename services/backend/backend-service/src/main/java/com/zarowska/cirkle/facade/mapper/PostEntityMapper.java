package com.zarowska.cirkle.facade.mapper;

import com.zarowska.cirkle.api.model.Post;
import com.zarowska.cirkle.domain.entity.FileInfoEntity;
import com.zarowska.cirkle.domain.entity.PostEntity;
import com.zarowska.cirkle.domain.entity.PostImage;
import java.net.URI;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
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
				post.getImages().stream().map(PostImage::getImage).map(FileInfoEntity::getId)
						.map(it -> "/images/%s".formatted(it)).map(URI::create).toList());
	}
}
