package com.zarowska.cirkle.api;

import com.zarowska.cirkle.AbstractTest;
import com.zarowska.cirkle.utils.TestUserContext;
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

	@Disabled
	@Test
	void testSendingMessageToUserById_Succeeds() {
		// Message message = new Message();
		//
		// bobContest.getApi().messages().sendMessageToUserById(bobContest.getUserId());
		// bobContest.getApi().messages().deleteMessageById(bobContest.getUserId());

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
