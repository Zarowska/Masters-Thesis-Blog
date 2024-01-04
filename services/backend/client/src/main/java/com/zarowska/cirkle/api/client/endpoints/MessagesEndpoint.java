package com.zarowska.cirkle.api.client.endpoints;

import com.zarowska.cirkle.api.client.RestTemplateWrapper;
import com.zarowska.cirkle.api.model.Message;
import com.zarowska.cirkle.api.model.MessageEventList;
import com.zarowska.cirkle.api.model.MessagePage;
import com.zarowska.cirkle.api.model.UpdateUserMessageRequest;
import java.util.Optional;

public class MessagesEndpoint extends AbstractClientEndpoint {

	public MessagesEndpoint(RestTemplateWrapper restTemplateWrapper) {
		super(restTemplateWrapper);
	}

	public Optional<Void> deleteMessageById(String messageId) {
		return null;
	}

	public Optional<Message> getMessageById(String messageId) {
		return null;
	}

	public Optional<MessagePage> getMessagesByUserId(String userId, Integer page, Integer size) {
		return null;
	}

	public MessageEventList getUnreadMessageEvents() {
		return null;
	}

	public Optional<Void> markMessageReadById(String messageId) {
		return null;
	}

	public Optional<Void> sendMessageToUserById(String userId) {
		return null;
	}

	public Optional<Message> updateMessageById(String messageId, UpdateUserMessageRequest updateUserMessageRequest) {
		return null;
	}
}
