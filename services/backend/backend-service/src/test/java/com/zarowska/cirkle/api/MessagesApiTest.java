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
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MessagesApiTest extends AbstractTest {

	// done // send-message-to-user-by-id
	// done // Get message by id Succeeds
	// done// Get message by id ThrowException
	// Update message by id Succeeds
	// Update message by id ThrowException
	// get-messages-by-user-id
	// Unread messages events
	// Mark message readed by id
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

		List<URI> imagesListURI = Stream.of("max_payne.png", "blazkovic.png")
				.map(it -> getFileFromResource("files/" + it))
				.map(imageResource -> bobContest.getApi().images().uploadImage(imageResource)).map(FileDto::getUrl)
				.toList();

		CreateMessageRequest request = CreateMessageRequest.builder().text("message").images(imagesListURI).build();
		Message newMessage = bobContest.getApi().messages().sendMessageToUserById(maxContext.getUserId(), request)
				.get();

		UUID newMessageId = newMessage.getId();

		Message messageBobSee = bobContest.getApi().messages().getMessageById(newMessageId).get();
		Message messageMaxSee = maxContext.getApi().messages().getMessageById(newMessageId).get();

		assertEquals(newMessage, messageBobSee);
		assertEquals(newMessage, messageMaxSee);
	};

	@Test
	void testGetingMessagesByUserId_ThrowException() {

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
	void test() {

		List<URI> imagesListURI = Stream.of("max_payne.png", "blazkovic.png")
				.map(it -> getFileFromResource("files/" + it))
				.map(imageResource -> bobContest.getApi().images().uploadImage(imageResource)).map(FileDto::getUrl)
				.toList();

		CreateMessageRequest request = CreateMessageRequest.builder().text("message").images(imagesListURI).build();
		Message newMessage = bobContest.getApi().messages().sendMessageToUserById(maxContext.getUserId(), request)
				.get();

		newMessage.getText();
		newMessage.getImages();
		newMessage.getUpdatedAt();
		newMessage.getCreatedAt();
		newMessage.getReceiver();
		newMessage.getSender();

		CreateMessageRequest request2 = CreateMessageRequest.builder().text("message2").build();
		Message newMessage2 = bobContest.getApi().messages().sendMessageToUserById(maxContext.getUserId(), request2)
				.get();

		newMessage2.getText();
		newMessage2.getImages();
		newMessage2.getUpdatedAt();
		newMessage2.getCreatedAt();
		newMessage2.getReceiver();
		newMessage2.getSender();

		assertEquals("message", newMessage.getText().toString());
		assertEquals("message2", newMessage2.getText().toString());

		/// users/{userId}/messages
		// description: Get dialog with user
		// operationId: get-messages-by-user-id
	};

}
