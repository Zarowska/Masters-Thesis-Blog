package com.zarowska.cirkle.facade.mapper;

import com.zarowska.cirkle.api.model.MessageEvent;
import com.zarowska.cirkle.domain.entity.MessageEventEntity;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class MessageEventMapper {

	private final ZoneOffset currentZoneOffset = OffsetDateTime.now().getOffset();

	public MessageEvent toDto(MessageEventEntity messageEventEntity) {
		return new MessageEvent();
	}

	// private final UserEntityMapper userEntityMapper;

	// public MessageEvent toDto(MessageEntity message) {
	// User sender = userEntityMapper.toDto(message.getSender());
	// User receiver = userEntityMapper.toDto(message.getReceiver());
	// OffsetDateTime createdAt =
	// message.getCreatedAt().atOffset(currentZoneOffset);
	// OffsetDateTime updatedAt =
	// message.getUpdatedAt().atOffset(currentZoneOffset);
	// List<URI> images =
	// message.getImages().stream().map(MessageImage::getImage).map(FileInfoEntity::getId)
	// .map(it -> "/images/%s".formatted(it)).map(URI::create).toList();
	// return new MessageEvent(message.getId(), sender, receiver, message.getText(),
	// images, createdAt, updatedAt);
	// }
}
