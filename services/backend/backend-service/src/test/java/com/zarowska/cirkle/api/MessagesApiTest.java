package com.zarowska.cirkle.api;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import com.zarowska.cirkle.AbstractTest;
import com.zarowska.cirkle.api.model.*;
import com.zarowska.cirkle.exception.CirkleException;
import com.zarowska.cirkle.utils.TestUserContext;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MessagesApiTest extends AbstractTest {

	// done // send-message-to-user-by-id
	// done // Get message by id Succeeds
	// done // Get message by id ThrowException
	// done // Update message by id Succeeds
	// done // Update message by id ThrowException
	// done // get-messages-by-user-id Succeeds
	// done // get-messages-by-user-id EmptyPage
	// done // Unread messages events
	// done // Mark message readed by id
	// Delete message by id

	TestUserContext bobContest, maxContext;

	@BeforeEach
	void setUp() {
		bobContest = context("Bob Marley", "bob@email", "http://avatar");
		bobContest.getApi().api().apiInfo();
		maxContext = context("Max Payne", "max@email", "http://avatar2");
		maxContext.getApi().api().apiInfo();
	}

	@Test
	void testGetUnreadMessageEvents_Succeeds() {
		CreateMessageRequest request = CreateMessageRequest.builder().text("message1").build();
		Message newMessage = bobContest.getApi().messages().sendMessageToUserById(maxContext.getUserId(), request)
				.get();
		assertFalse(newMessage.getViewedByReceiver());

		CreateMessageRequest request2 = CreateMessageRequest.builder().text("message2").build();
		Message newMessage2 = bobContest.getApi().messages().sendMessageToUserById(maxContext.getUserId(), request2)
				.get();
		assertFalse(newMessage2.getViewedByReceiver());

		CreateMessageRequest request3 = CreateMessageRequest.builder().text("message2").build();
		Message newMessage3 = bobContest.getApi().messages().sendMessageToUserById(maxContext.getUserId(), request3)
				.get();
		assertFalse(newMessage3.getViewedByReceiver());

		Optional<MessageEventList> maxMessageEventList = maxContext.getApi().messages().getUnreadMessageEvents();
		assertNotNull(maxMessageEventList.get());

		assertEquals("3", maxMessageEventList.get().getItems().get(0).getCount().toString());
		assertEquals("Bob Marley", maxMessageEventList.get().getItems().get(0).getAuthor().getName());

		maxContext.getApi().messages().getMessageById(newMessage3.getId());

		Optional<MessageEventList> maxMessageEventList2 = maxContext.getApi().messages().getUnreadMessageEvents();
		assertNotNull(maxMessageEventList2.get());

		assertEquals("2", maxMessageEventList2.get().getItems().get(0).getCount().toString());

	}

	@Test
	void testMarkMessageReadById_Succeeds() {
		List<URI> imagesListURI = Stream.of("max_payne.png", "blazkovic.png")
				.map(it -> getFileFromResource("files/" + it))
				.map(imageResource -> bobContest.getApi().images().uploadImage(imageResource)).map(FileDto::getUrl)
				.toList();
		CreateMessageRequest request = CreateMessageRequest.builder().text("message").images(imagesListURI).build();
		Message newMessage = bobContest.getApi().messages().sendMessageToUserById(maxContext.getUserId(), request)
				.get();
		UUID newMessageId = newMessage.getId();

		assertFalse(newMessage.getViewedByReceiver());

		maxContext.getApi().messages().markMessageReadById(newMessageId);

		assertTrue(bobContest.getApi().messages().getMessageById(newMessageId).get().getViewedByReceiver());

	}

	@Test
	void testMarkMessageReadById__ThrowException() {
		List<URI> imagesListURI = Stream.of("max_payne.png", "blazkovic.png")
				.map(it -> getFileFromResource("files/" + it))
				.map(imageResource -> bobContest.getApi().images().uploadImage(imageResource)).map(FileDto::getUrl)
				.toList();
		CreateMessageRequest request = CreateMessageRequest.builder().text("message").images(imagesListURI).build();
		Message newMessage = bobContest.getApi().messages().sendMessageToUserById(maxContext.getUserId(), request)
				.get();
		UUID newMessageId = newMessage.getId();

		assertFalse(newMessage.getViewedByReceiver());

		Exception exception = assertThrows(CirkleException.class, () -> {
			bobContest.getApi().messages().markMessageReadById(newMessageId);
		});

		assertEquals("Only the message receiver is allowed to mark it as read.", exception.getMessage());

	}

	@Test
	void testSendingMessageToUserById_Succeeds() {
		List<URI> imagesListURI = Stream.of("max_payne.png", "blazkovic.png")
				.map(it -> getFileFromResource("files/" + it))
				.map(imageResource -> bobContest.getApi().images().uploadImage(imageResource)).map(FileDto::getUrl)
				.toList();
		CreateMessageRequest request = CreateMessageRequest.builder().text("New message").images(imagesListURI).build();
		Optional<Message> newMessage = bobContest.getApi().messages().sendMessageToUserById(maxContext.getUserId(),
				request);
		assertTrue(newMessage.isPresent());
	};

	@Test
	void testUpdateMessageById_Succeeds() {

		List<URI> imagesListURI = Stream.of("max_payne.png", "blazkovic.png")
				.map(it -> getFileFromResource("files/" + it))
				.map(imageResource -> bobContest.getApi().images().uploadImage(imageResource)).map(FileDto::getUrl)
				.toList();

		CreateMessageRequest request = CreateMessageRequest.builder().text("message").images(imagesListURI).build();
		Message messageBeforUpdate = bobContest.getApi().messages()
				.sendMessageToUserById(maxContext.getUserId(), request).get();

		UUID newMessageId = messageBeforUpdate.getId();

		assertEquals(messageBeforUpdate.getText(), "message");

		List<URI> imagesListURI2 = new ArrayList<>();

		UpdateUserMessageRequest updateUserMessageRequest = UpdateUserMessageRequest.builder().text("message2")
				.images(imagesListURI2).build();
		Message messageAfterUpdate = bobContest.getApi().messages()
				.updateMessageById(newMessageId, updateUserMessageRequest).get();

		assertEquals("message2", messageAfterUpdate.getText().toString());
		assertEquals(messageBeforUpdate.getId(), messageAfterUpdate.getId());
		assertEquals(imagesListURI2, messageAfterUpdate.getImages());
		assertEquals(messageBeforUpdate.getCreatedAt(), messageAfterUpdate.getCreatedAt());
		assertNotEquals(messageBeforUpdate.getCreatedAt(), messageAfterUpdate.getUpdatedAt());
	};

	@Test
	void testUpdateMessageById_ThrowException() {
		List<URI> imagesListURI = Stream.of("max_payne.png", "blazkovic.png")
				.map(it -> getFileFromResource("files/" + it))
				.map(imageResource -> bobContest.getApi().images().uploadImage(imageResource)).map(FileDto::getUrl)
				.toList();

		CreateMessageRequest request = CreateMessageRequest.builder().text("message").images(imagesListURI).build();
		Message messageBeforUpdate = bobContest.getApi().messages()
				.sendMessageToUserById(maxContext.getUserId(), request).get();

		UUID messageBeforUpdateId = messageBeforUpdate.getId();

		assertEquals(messageBeforUpdate.getText(), "message");

		List<URI> imagesListURI2 = new ArrayList<>();

		TestUserContext johnContest = context("John Lennon", "john@email", "http://avatar3");
		johnContest.getApi().api().apiInfo();
		UpdateUserMessageRequest updateUserMessageRequest = UpdateUserMessageRequest.builder().text("message2")
				.images(imagesListURI2).build();
		Exception exception = assertThrows(CirkleException.class, () -> {
			johnContest.getApi().messages().updateMessageById(messageBeforUpdateId, updateUserMessageRequest);
		});
		String expectedMessage = "Only sender can update message";
		assertEquals(expectedMessage, exception.getMessage());

	};

	@Test
	void testGetingMessagesByUserId_Succeeds() {

		TestUserContext johnContest = context("John Lennon", "john@email", "http://avatar3");
		johnContest.getApi().api().apiInfo();

		CreateMessageRequest request0 = CreateMessageRequest.builder().text("message0").build();
		johnContest.getApi().messages().sendMessageToUserById(maxContext.getUserId(), request0);

		List<URI> imagesListURI = Stream.of("max_payne.png", "blazkovic.png")
				.map(it -> getFileFromResource("files/" + it))
				.map(imageResource -> bobContest.getApi().images().uploadImage(imageResource)).map(FileDto::getUrl)
				.toList();

		CreateMessageRequest request = CreateMessageRequest.builder().text("message1").images(imagesListURI).build();
		Message newMessage = bobContest.getApi().messages().sendMessageToUserById(maxContext.getUserId(), request)
				.get();

		CreateMessageRequest request2 = CreateMessageRequest.builder().text("message2").images(imagesListURI).build();
		Message newMessage2 = bobContest.getApi().messages().sendMessageToUserById(maxContext.getUserId(), request2)
				.get();

		Optional<MessagePage> messagePage = bobContest.getApi().messages().getMessagesByUserId(maxContext.getUserId());

		List<String> allMessageText = messagePage.get().getContent().stream().map(Message::getText).toList();

		List<String> expectedMessageText = List.of("message1", "message2");

		assertEquals(expectedMessageText, allMessageText);

	};

	@Test
	void testGetingMessagesByUserId_ReturnEmptyPage() {

		List<URI> imagesListURI = Stream.of("max_payne.png", "blazkovic.png")
				.map(it -> getFileFromResource("files/" + it))
				.map(imageResource -> bobContest.getApi().images().uploadImage(imageResource)).map(FileDto::getUrl)
				.toList();

		CreateMessageRequest request = CreateMessageRequest.builder().text("message").images(imagesListURI).build();
		Message newMessage = bobContest.getApi().messages().sendMessageToUserById(maxContext.getUserId(), request)
				.get();

		TestUserContext johnContest = context("John Lennon", "john@email", "http://avatar3");
		johnContest.getApi().api().apiInfo();

		Optional<MessagePage> messagePage = johnContest.getApi().messages().getMessagesByUserId(bobContest.getUserId());

		List<String> allMessageText = messagePage.get().getContent().stream()
				.map(m -> m.getSender().toString() + " - " + m.getReceiver().toString() + " : " + m.getText())
				.collect(Collectors.toList());

		List<String> expectedMessageText = List.of();

		assertEquals(expectedMessageText, allMessageText);
	};

	@Test
	void testGetingMessageById_ThrowException() {
		List<URI> imagesListURI = Stream.of("max_payne.png", "blazkovic.png")
				.map(it -> getFileFromResource("files/" + it))
				.map(imageResource -> bobContest.getApi().images().uploadImage(imageResource)).map(FileDto::getUrl)
				.toList();

		CreateMessageRequest request = CreateMessageRequest.builder().text("message").images(imagesListURI).build();
		Message newMessage = bobContest.getApi().messages().sendMessageToUserById(maxContext.getUserId(), request)
				.get();

		UUID newMessageId = newMessage.getId();

		TestUserContext johnContest = context("John Lennon", "john@email", "http://avatar3");
		johnContest.getApi().api().apiInfo();
		Exception exception = assertThrows(CirkleException.class, () -> {
			johnContest.getApi().messages().getMessageById(newMessageId);
		});
		String expectedMessage = "Only receiver and sender can message.";
		assertEquals(expectedMessage, exception.getMessage());
	};

	@Test
	void testGetingMessageById_Succeeds() {
		List<URI> imagesListURI = Stream.of("max_payne.png", "blazkovic.png")
				.map(it -> getFileFromResource("files/" + it))
				.map(imageResource -> bobContest.getApi().images().uploadImage(imageResource)).map(FileDto::getUrl)
				.toList();

		CreateMessageRequest request = CreateMessageRequest.builder().text("message").images(imagesListURI).build();
		Message newMessage = bobContest.getApi().messages().sendMessageToUserById(maxContext.getUserId(), request)
				.get();

		UUID newMessageId = newMessage.getId();

		Message messageBobGot = bobContest.getApi().messages().getMessageById(newMessageId).get();

		assertEquals(newMessage, messageBobGot);

		Message messageMaxGot = maxContext.getApi().messages().getMessageById(newMessageId).get();

		assertEquals(newMessage, messageMaxGot);
	};

}
