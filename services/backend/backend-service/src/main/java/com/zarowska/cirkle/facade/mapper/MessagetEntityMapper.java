package com.zarowska.cirkle.facade.mapper;

import com.zarowska.cirkle.domain.entity.*;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessagetEntityMapper {

	private final ZoneOffset currentZoneOffset = OffsetDateTime.now().getOffset();

	private final UserEntityMapper userEntityMapper;

	// public Message toDto(MessageEntity message) {
	// List<URI> images =
	// message.getImages().stream().map(MessageImage::getImage).map(FileInfoEntity::getId)
	// .map(it -> "/images/%s".formatted(it)).map(URI::create).toList();
	//
	// return new Message(message.getId(), message.getText(),
	// message.getCreatedAt().atOffset(currentZoneOffset),
	// message.getUpdatedAt().atOffset(currentZoneOffset),
	// userEntityMapper.toDto(message.getSender()),
	// userEntityMapper.toDto(message.getReceiver()), images);
	//
	// }

}
