package com.zarowska.cirkle.domain.service;

import com.zarowska.cirkle.domain.entity.MessageEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface MessageService {

	MessageEntity save(MessageEntity messageEntity);

	Optional<MessageEntity> findById(UUID messageId);

	Page<MessageEntity> findByUsersId(UUID currentUserId, UUID userId, PageRequest of);

	// List<MessageEntity> findUnreadMessagesByUserId(UUID id);
}
