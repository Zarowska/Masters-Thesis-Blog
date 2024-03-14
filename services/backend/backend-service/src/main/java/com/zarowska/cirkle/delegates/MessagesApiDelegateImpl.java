package com.zarowska.cirkle.delegates;

import com.zarowska.cirkle.api.model.Message;
import com.zarowska.cirkle.api.model.MessageEventList;
import com.zarowska.cirkle.api.model.UpdateUserMessageRequest;
import com.zarowska.cirkle.api.rest.MessagesApiDelegate;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessagesApiDelegateImpl implements MessagesApiDelegate {

	@Override
	public ResponseEntity<Void> deleteMessageById(UUID messageId) {
		return MessagesApiDelegate.super.deleteMessageById(messageId);
	}

	@Override
	public ResponseEntity<Message> getMessageById(UUID messageId) {
		return MessagesApiDelegate.super.getMessageById(messageId);
	}
	@Override
	public ResponseEntity<MessageEventList> getUnreadMessageEvents() {
		return MessagesApiDelegate.super.getUnreadMessageEvents();
	}

	@Override
	public ResponseEntity<Void> markMessageReadById(UUID messageId) {
		return MessagesApiDelegate.super.markMessageReadById(messageId);
	}

	// @Override
	// public ResponseEntity<Void> sendMessageToUserById(,UUID userId) {
	// return MessagesApiDelegate.super.sendMessageToUserById(userId);
	// }

	@Override
	public ResponseEntity<Message> updateMessageById(UUID messageId,
			UpdateUserMessageRequest updateUserMessageRequest) {
		return MessagesApiDelegate.super.updateMessageById(messageId, updateUserMessageRequest);
	}
}
