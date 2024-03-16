package com.zarowska.cirkle.api;

import com.zarowska.cirkle.AbstractTest;
import com.zarowska.cirkle.api.model.*;
import com.zarowska.cirkle.exception.CirkleException;
import com.zarowska.cirkle.utils.TestUserContext;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class MessagesApiTest extends AbstractTest {

	// done // send-message-to-user-by-id
	// Get message by id
	// Update message by id
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
		Message newMessage = bobContest.getApi().messages().sendMessageToUserById(maxContext.getUserId(), request)
				.get();

		UUID newMessageId = newMessage.getId();

		Message messageById = maxContext.getApi().messages().getMessageById(newMessageId).get();

		assertEquals(newMessage, messageById);

	};

	@Test
	void testUpdateMessageById_ThrowException() {

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

	@Disabled
	@Test
	void testGetingMessagesByUserId_Succeeds() {

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
