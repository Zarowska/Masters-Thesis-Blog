package com.zarowska.cirkle.delegates;

import com.zarowska.cirkle.api.model.Message;
import com.zarowska.cirkle.api.model.MessageEventList;
import com.zarowska.cirkle.api.model.MessagePage;
import com.zarowska.cirkle.api.model.UpdateUserMessageRequest;
import com.zarowska.cirkle.api.rest.MessagesApiDelegate;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessagesApiDelegateImpl implements MessagesApiDelegate {

	@Override
	public ResponseEntity<Void> deleteMessageById(String messageId) {
		return MessagesApiDelegate.super.deleteMessageById(messageId);
	}

	@Override
	public ResponseEntity<Message> getMessageById(String messageId) {
		return MessagesApiDelegate.super.getMessageById(messageId);
	}
	@Override
	public ResponseEntity<MessageEventList> getUnreadMessageEvents() {
		return MessagesApiDelegate.super.getUnreadMessageEvents();
	}

	@Override
	public ResponseEntity<Void> markMessageReadById(String messageId) {
		return MessagesApiDelegate.super.markMessageReadById(messageId);
	}

	@Override
	public ResponseEntity<Void> sendMessageToUserById(String userId) {
		return MessagesApiDelegate.super.sendMessageToUserById(userId);
	}

	@Override
	public ResponseEntity<Message> updateMessageById(String messageId,
			UpdateUserMessageRequest updateUserMessageRequest) {
		return MessagesApiDelegate.super.updateMessageById(messageId, updateUserMessageRequest);
	}
}
