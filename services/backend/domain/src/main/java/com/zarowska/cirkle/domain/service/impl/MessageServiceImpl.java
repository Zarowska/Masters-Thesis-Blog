package com.zarowska.cirkle.domain.service.impl;

import com.zarowska.cirkle.domain.entity.MessageEntity;
import com.zarowska.cirkle.domain.repository.MessageEntityRepository;
import com.zarowska.cirkle.domain.service.MessageService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

	private final MessageEntityRepository messageEntityRepository;

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public MessageEntity save(MessageEntity messageEntity) {
		return messageEntityRepository.save(messageEntity);
	}

	@Override
	public Optional<MessageEntity> findById(UUID messageId) {
		return messageEntityRepository.findById(messageId);
	}
}
