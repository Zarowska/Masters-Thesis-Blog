package com.zarowska.cirkle.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import com.zarowska.cirkle.AbstractTest;
import com.zarowska.cirkle.api.model.*;
import com.zarowska.cirkle.exception.CirkleException;
import com.zarowska.cirkle.utils.TestUserContext;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RelationsApiTest extends AbstractTest {

	// done //sendFriendshipRequest
	// done // getFriendshipRequestById
	// done // findAllFriendshipRequests

	// done //You cannot send a Friendship Request if you are already Friends
	// done //only Receiver can see Requests

	// done //acceptFriendshipRequestById
	// done //rejectFriendshipRequestById

	// done // getUsersFriendsById
	// done deleteFriendFromUsersFriendsByIds - Unfriend user by id

	TestUserContext bobContest, maxContext;

	@BeforeEach
	void setUp() {
		bobContest = context("Bob Marley", "bob@email", "http://avatar");
		bobContest.getApi().api().apiInfo(); // Simulating API call to setup user
		maxContext = context("Max Payne", "max@email", "http://avatar2");
		maxContext.getApi().api().apiInfo(); // Simulating API call to setup user
	}

	@Test
	void testSendingFriendRequest_Succeeds() {
		// bob send friendship request to max
		bobContest.getApi().relations().sendFriendshipRequest(maxContext.getUserId());
		// max checks incoming friendship requests
		FriendshipRequestList allFriendshipRequests = maxContext.getApi().relations().findAllFriendshipRequests();
		// max finds bob's friendship request
		Optional<FriendshipRequest> request = allFriendshipRequests.getItems().stream()
				.filter(it -> it.getOwner().getId().equals(bobContest.getUserId())).findFirst();
		assertTrue(request.isPresent());
	}

	@Test
	void testGetingFrindshipRequestById_Succeeds() {
		bobContest.getApi().relations().sendFriendshipRequest(maxContext.getUserId());
		FriendshipRequestList allFriendshipRequests = maxContext.getApi().relations().findAllFriendshipRequests();
		Optional<FriendshipRequest> request = allFriendshipRequests.getItems().stream()
				.filter(it -> it.getOwner().getId().equals(bobContest.getUserId())).findFirst();
		assertTrue(request.isPresent());
		UUID requestId = request.get().getId();
		Optional<FriendshipRequest> requestById = maxContext.getApi().relations().getFriendshipRequestById(requestId);
		assertEquals(request.get(), requestById.get());
	}

	@Test
	void testGetFriendshipRequestById_ShouldThrowException_NotFound() {
		UUID requestId = UUID.randomUUID();
		Exception exception = assertThrows(CirkleException.class, () -> {
			maxContext.getApi().relations().getFriendshipRequestById(requestId);
		});
		String expectedMessage = "Friendship request not found with id=" + requestId;
		assertEquals(expectedMessage, exception.getMessage());
	}

	@Test
	void getFrindshipRequestById_ShouldThrowException_OnlyReceiverCanSeenRequests() {
		String ownerRequest;
		bobContest.getApi().relations().sendFriendshipRequest(maxContext.getUserId());
		FriendshipRequestList allFriendshipRequests = maxContext.getApi().relations().findAllFriendshipRequests();
		Optional<FriendshipRequest> request = allFriendshipRequests.getItems().stream()
				.filter(it -> it.getOwner().getId().equals(bobContest.getUserId())).findFirst();
		assertTrue(request.isPresent());

		UUID requestId = request.get().getId();
		TestUserContext johnContest = context("John Lennon", "john@email", "http://avatar3");
		johnContest.getApi().api().apiInfo();

		Exception exception = assertThrows(CirkleException.class, () -> {
			johnContest.getApi().relations().getFriendshipRequestById(requestId);
		});
		String expectedMessage = "Only receiver can seen friendship requests";
		assertEquals(expectedMessage, exception.getMessage());
	}

	@Test
	void sendFrindshipRequest_ShouldThrowException() {
		bobContest.getApi().relations().sendFriendshipRequest(maxContext.getUserId());
		FriendshipRequestList allFriendshipRequests = maxContext.getApi().relations().findAllFriendshipRequests();
		Optional<FriendshipRequest> request = allFriendshipRequests.getItems().stream()
				.filter(it -> it.getOwner().getId().equals(bobContest.getUserId())).findFirst();
		maxContext.getApi().relations().acceptFriendshipRequestById(request.get().getId());

		Exception exception = assertThrows(CirkleException.class, () -> {
			maxContext.getApi().relations().sendFriendshipRequest(bobContest.getUserId());
		});

		String expectedMessage = "Users are already friends";
		assertEquals(expectedMessage, exception.getMessage());

		Exception exception2 = assertThrows(CirkleException.class, () -> {
			bobContest.getApi().relations().sendFriendshipRequest(maxContext.getUserId());
		});
		assertEquals(expectedMessage, exception2.getMessage());
	}

	@Test
	void listAllFriendshipRequests_ShouldSucceed() {
		bobContest.getApi().relations().sendFriendshipRequest(maxContext.getUserId());
		TestUserContext johnContest = context("John Lennon", "john@email", "http://avatar3");
		johnContest.getApi().relations().sendFriendshipRequest(maxContext.getUserId());
		FriendshipRequestList allFriendshipRequests = maxContext.getApi().relations().findAllFriendshipRequests();
		List<String> owners = allFriendshipRequests.getItems().stream().map(it -> it.getOwner().getName()).toList();
		assertThat(owners).contains("John Lennon", "Bob Marley");
	}

	@Test
	void acceptFriendshipRequestById_and_getUsersFriendsById_ShouldSucceed() {
		bobContest.getApi().relations().sendFriendshipRequest(maxContext.getUserId());
		FriendshipRequestList allFriendshipRequests = maxContext.getApi().relations().findAllFriendshipRequests();
		Optional<FriendshipRequest> request = allFriendshipRequests.getItems().stream()
				.filter(it -> it.getOwner().getId().equals(bobContest.getUserId())).findFirst();
		assertTrue(request.isPresent());

		maxContext.getApi().relations().acceptFriendshipRequestById(request.get().getId());

		Optional<UserPage> maxFriends = bobContest.getApi().relations().getUsersFriendsById(maxContext.getUserId(), 0,
				100);

		assertTrue(maxFriends.isPresent());

		Optional<User> bob = maxFriends.get().getContent().stream()
				.filter(it -> it.getId().equals(bobContest.getUserId())).findFirst();

		assertTrue(bob.isPresent());
	}

	@Test
	void rejectFriendshipRequestById_Succeeds() {
		bobContest.getApi().relations().sendFriendshipRequest(maxContext.getUserId());
		FriendshipRequestList allFriendshipRequests = maxContext.getApi().relations().findAllFriendshipRequests();
		Optional<FriendshipRequest> request = allFriendshipRequests.getItems().stream()
				.filter(it -> it.getOwner().getId().equals(bobContest.getUserId())).findFirst();

		assertTrue(request.isPresent());

		maxContext.getApi().relations().rejectFriendshipRequestById(request.get().getId());

		Exception exception = assertThrows(CirkleException.class, () -> {
			maxContext.getApi().relations().rejectFriendshipRequestById(request.get().getId());
		});
		String expectedMessage = "Friendship request not found with id=" + request.get().getId();
		assertEquals(expectedMessage, exception.getMessage());
	}

	@Test
	void testDeleteFriendFromUsersFriendsById_Succeeds() {
		createMaxBobFriendship();
		assertTrue(checkMaxBobFriendshipExit());
		bobContest.getApi().relations().deleteFriendFromUsersFriendsById(bobContest.getUserId(),
				maxContext.getUserId());
		assertFalse(checkMaxBobFriendshipExit());

	}

	@Test
	void testDeleteFriend_ShouldThrowExceptionForNonFriends() {
		createMaxBobFriendship();
		assertTrue(checkMaxBobFriendshipExit());

		TestUserContext johnContest = context("John Lennon", "john@email", "http://avatar3");
		johnContest.getApi().api().apiInfo();

		Exception exception = assertThrows(CirkleException.class, () -> {
			johnContest.getApi().relations().deleteFriendFromUsersFriendsById(bobContest.getUserId(),
					maxContext.getUserId());
		});
		assertEquals("Only friends can delete own friendship.", exception.getMessage());
		assertTrue(checkMaxBobFriendshipExit());
	}

	private void createMaxBobFriendship() {
		bobContest.getApi().relations().sendFriendshipRequest(maxContext.getUserId());
		FriendshipRequestList allFriendshipRequests = maxContext.getApi().relations().findAllFriendshipRequests();
		Optional<FriendshipRequest> request = allFriendshipRequests.getItems().stream()
				.filter(it -> it.getOwner().getId().equals(bobContest.getUserId())).findFirst();
		assertTrue(request.isPresent());
		maxContext.getApi().relations().acceptFriendshipRequestById(request.get().getId());
	}

	private boolean checkMaxBobFriendshipExit() {
		Optional<UserPage> maxFriends = bobContest.getApi().relations().getUsersFriendsById(maxContext.getUserId(), 0,
				100);
		assertTrue(maxFriends.isPresent());
		Optional<User> bob = maxFriends.get().getContent().stream()
				.filter(it -> it.getId().equals(bobContest.getUserId())).findFirst();
		return bob.isPresent();
	}

}
