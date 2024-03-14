package com.zarowska.cirkle.api.client.endpoints;

import com.zarowska.cirkle.api.client.RestTemplateWrapper;
import com.zarowska.cirkle.api.model.*;
import java.util.Optional;
import java.util.UUID;

public class MessagesEndpoint extends AbstractClientEndpoint {

	public MessagesEndpoint(RestTemplateWrapper restTemplateWrapper) {
		super(restTemplateWrapper);
	}

	public Optional<Void> deleteMessageById(UUID messageId) {
		return null;
	}

	public Optional<Message> getMessageById(UUID messageId) {
		return null;
	}

	public Optional<MessagePage> getMessagesByUserId(UUID userId, Integer page, Integer size) {
		return null;
	}

	public MessageEventList getUnreadMessageEvents() {
		return null;
	}

	public Optional<Void> markMessageReadById(UUID messageId) {
		return null;
	}

	public Optional<Message> sendMessageToUserById(UUID userId, CreateMessageRequest createMessageRequest) {
		return doCall(() -> restTemplateWrapper.post(createMessageRequest, Message.class, "/users/{userId}/messages",
				userId));
	}

	public Optional<Message> updateMessageById(UUID messageId, UpdateUserMessageRequest updateUserMessageRequest) {
		return null;
	}
}
