package com.zarowska.cirkle.facade;

import com.zarowska.cirkle.api.model.*;

import java.util.List;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

public interface MessageFacade {

	@Transactional
	Message sendMessageToUserById(UUID userId, CreateMessageRequest createMessageRequest);

	@Transactional
	Message getMessageById(UUID messageId);

	@Transactional
	Message updateMessageById(UUID messageId, UpdateUserMessageRequest updateUserMessageRequest);

	@Transactional
	MessagePage getMessagesByUserId(UUID userId, Integer page, Integer size);

	@Transactional
	List<Message> getUnreadMessageEvents();
}
