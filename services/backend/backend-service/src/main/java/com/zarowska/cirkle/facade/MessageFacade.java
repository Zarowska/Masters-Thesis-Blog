package com.zarowska.cirkle.facade;

import com.zarowska.cirkle.api.model.CreateMessageRequest;
import com.zarowska.cirkle.api.model.Message;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

public interface MessageFacade {

	@Transactional
	Message sendMessageToUserById(UUID userId, CreateMessageRequest createMessageRequest);

	@Transactional
	Message getMessageById(UUID messageId);
}
