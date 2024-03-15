package com.zarowska.cirkle.facade.mapper;

import com.zarowska.cirkle.api.model.Message;
import com.zarowska.cirkle.api.model.User;
import com.zarowska.cirkle.domain.entity.*;
import java.net.URI;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessagetEntityMapper {

	private final ZoneOffset currentZoneOffset = OffsetDateTime.now().getOffset();

	private final UserEntityMapper userEntityMapper;

	public Message toDto(MessageEntity message) {
		User sender = userEntityMapper.toDto(message.getSender());
		User receiver = userEntityMapper.toDto(message.getReceiver());
		OffsetDateTime createdAt = message.getCreatedAt().atOffset(currentZoneOffset);
		OffsetDateTime updatedAt = message.getUpdatedAt().atOffset(currentZoneOffset);
		List<URI> images = message.getImages().stream().map(MessageImage::getImage).map(FileInfoEntity::getId)
				.map(it -> "/images/%s".formatted(it)).map(URI::create).toList();
		return new Message(message.getId(), sender, receiver, message.getText(), images, createdAt, updatedAt);
	}
}
