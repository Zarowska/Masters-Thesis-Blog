package com.zarowska.cirkle.api;

import static org.junit.Assert.assertTrue;

import com.zarowska.cirkle.AbstractTest;
import com.zarowska.cirkle.api.model.*;
import com.zarowska.cirkle.utils.TestUserContext;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class MessagesApiTest extends AbstractTest {

	// send-message-to-user-by-id
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
		bobContest.getApi().api().apiInfo(); // Simulating API call to setup user
		maxContext = context("Max Payne", "max@email", "http://avatar2");
		maxContext.getApi().api().apiInfo(); // Simulating API call to setup user
	}

	@Test
	void testSendingMessageToUserById_Succeeds() {

		List<URI> imagesListURI = Stream.of("max_payne.png", "blazkovic.png")
				.map(it -> getFileFromResource("files/" + it))
				.map(imageResource -> bobContest.getApi().images().uploadImage(imageResource)).map(FileDto::getUrl)
				.toList();
		CreateMessageRequest request = CreateMessageRequest.builder().text("New post").images(imagesListURI).build();
		Optional<Message> newMessage = bobContest.getApi().messages().sendMessageToUserById(maxContext.getUserId(),
				request);
		assertTrue(newMessage.isPresent());
	};

	@Disabled
	@Test
	void testUpdateMessageById_Succeeds() {

	};

	@Disabled
	@Test
	void testGetingMessagesByUserId_Succeeds() {
		/// users/{userId}/messages
		// description: Get dialog with user
		// operationId: get-messages-by-user-id
	};

}
