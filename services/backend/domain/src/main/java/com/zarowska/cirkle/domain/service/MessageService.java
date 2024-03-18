package com.zarowska.cirkle.domain.service;

import com.zarowska.cirkle.domain.entity.MessageEntity;
import java.util.Optional;
import java.util.UUID;

public interface MessageService {

	MessageEntity save(MessageEntity messageEntity);

	Optional<MessageEntity> findById(UUID messageId);
}
