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
		return doCall(() -> restTemplateWrapper.get(Message.class, "/user/messages/{messageId}", messageId));
	}

	public Optional<MessagePage> getMessagesByUserId(UUID userId) {
		return doCall(() -> restTemplateWrapper.get(MessagePage.class, "/users/{userId}/messages", userId));
	}

	public Optional<MessageEventList> getUnreadMessageEvents() {
		return doCall(() -> restTemplateWrapper.get(MessageEventList.class, "/user/messages/events"));
	}

	public Optional<Void> markMessageReadById(UUID messageId) {
		return null;
	}

	public Optional<Message> sendMessageToUserById(UUID userId, CreateMessageRequest createMessageRequest) {
		return doCall(() -> restTemplateWrapper.post(createMessageRequest, Message.class, "/users/{userId}/messages",
				userId));
	}

	public Optional<Message> updateMessageById(UUID messageId, UpdateUserMessageRequest updateUserMessageRequest) {
		return doCall(() -> restTemplateWrapper.put(updateUserMessageRequest, Message.class,
				"/user/messages/{messageId}", messageId));
	}
}
