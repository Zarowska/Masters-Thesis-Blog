package com.zarowska.cirkle.delegates;

import com.zarowska.cirkle.api.model.*;
import com.zarowska.cirkle.api.rest.MessagesApiDelegate;
import com.zarowska.cirkle.facade.MessageFacade;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessagesApiDelegateImpl implements MessagesApiDelegate {

	private final MessageFacade messageFacade;

	@Override
	public ResponseEntity<MessagePage> getMessagesByUserId(UUID userId, Integer page, Integer size) {
		return ResponseEntity.ok(messageFacade.getMessagesByUserId(userId, page, size));
	}
	@Override
	public ResponseEntity<Void> deleteMessageById(UUID messageId) {
		return MessagesApiDelegate.super.deleteMessageById(messageId);
	}

	@Override
	public ResponseEntity<Message> getMessageById(UUID messageId) {
		return ResponseEntity.ok(messageFacade.getMessageById(messageId));
	}
	@Override
	public ResponseEntity<List<Message>> getUnreadMessageEvents() {
		return ResponseEntity.ok(messageFacade.getUnreadMessageEvents());
	}

	@Override
	public ResponseEntity<Void> markMessageReadById(UUID messageId) {
		return MessagesApiDelegate.super.markMessageReadById(messageId);
	}

	@Override
	public ResponseEntity<Message> sendMessageToUserById(UUID userId, CreateMessageRequest createMessageRequest) {
		return ResponseEntity.ok(messageFacade.sendMessageToUserById(userId, createMessageRequest));
	}

	@Override
	public ResponseEntity<Message> updateMessageById(UUID messageId,
			UpdateUserMessageRequest updateUserMessageRequest) {
		return ResponseEntity.ok(messageFacade.updateMessageById(messageId, updateUserMessageRequest));
	}
}
