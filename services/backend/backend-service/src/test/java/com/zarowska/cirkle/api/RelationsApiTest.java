package com.zarowska.cirkle.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.zarowska.cirkle.AbstractTest;
import com.zarowska.cirkle.api.model.*;
import com.zarowska.cirkle.utils.TestUserContext;
import jakarta.validation.Valid;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RelationsApiTest extends AbstractTest {

	// acceptFriendshipRequestById
	// deleteFriendFromUsersFriendsByIds
	// findAllFriendshipRequests
	// getFriendshipRequestById
	// getUsersFriendsById
	// rejectFriendshipRequestById
	// done sendFriendshipRequest
	// Unfriend user by id

	TestUserContext bobContest, maxContext;

	@BeforeEach
	void setUp() {
		bobContest = context("Bob Marley", "bob@email", "http://avatar");
		bobContest.getApi().api().apiInfo(); // Simulating API call to setup user
		maxContext = context("Max Payne", "max@email", "http://avatar2");
		maxContext.getApi().api().apiInfo(); // Simulating API call to setup user
	}

	@Test
	void sendFrindshipRequest_ShouldSucceed() {
		// bob send friendship request to max
		bobContest.getApi().relations().sendFriendshipRequest(maxContext.getUserId());
		// max checks incoming friendship requests
		FriendshipRequestList allFriendshipRequests = maxContext.getApi().relations().findAllFriendshipRequests();
		// max finds bob's friendship request
		Optional<FriendshipRequest> request = allFriendshipRequests.getItems().stream()
				.filter(it -> it.getOwner().getId().equals(bobContest.getUserId())).findFirst();
		assertTrue(request.isPresent());
	}

	// TODO
	@Test
	void getFrindshipRequestById_ShouldSucceed() {
		// bob send friendship request to max
		bobContest.getApi().relations().sendFriendshipRequest(maxContext.getUserId());
		// max checks incoming friendship requests
		FriendshipRequestList allFriendshipRequests = maxContext.getApi().relations().findAllFriendshipRequests();
		// max finds bob's friendship request
		Optional<FriendshipRequest> request = allFriendshipRequests.getItems().stream()
				.filter(it -> it.getOwner().getId().equals(bobContest.getUserId())).findFirst();
		assertTrue(request.isPresent());
		UUID requestId = request.get().getId();
		Optional<FriendshipRequest> requestById = maxContext.getApi().relations()
				.getFriendshipRequestById(requestId.toString());
		assertEquals(request.get(), requestById.get());

	}

	@Test
	void nestedTest() throws Exception {
		context("Bob Marley", "bob@email", "http://avatar").apply(bobContest -> {
			context("Max Payne", "max@email", "http://avatar2").apply(maxContext -> {

				// bob send friendship request to max

				bobContest.getApi().relations().sendFriendshipRequest(maxContext.getUserId());

				// max checks incoming friendship requests

				FriendshipRequestList allFriendshipRequests = maxContext.getApi().relations()
						.findAllFriendshipRequests();

				// max finds bob's friendship request
				Optional<FriendshipRequest> request = allFriendshipRequests.getItems().stream()
						.filter(it -> it.getOwner().getId().equals(bobContest.getUserId())).findFirst();

				assertTrue(request.isPresent());

				// max accepts bob's friendship request
				maxContext.getApi().relations().acceptFriendshipRequestById(request.get().getId());

				// max checks his friends
				Optional<UserPage> maxFriends = bobContest.getApi().relations()
						.getUsersFriendsById(maxContext.getUserId(), 0, 100);

				assertTrue(maxFriends.isPresent());

				// max found bob in his friendship request
				Optional<@Valid User> bob = maxFriends.get().getContent().stream()
						.filter(it -> it.getId().equals(bobContest.getUserId())).findFirst();

				// bob now friend of max
				assertTrue(bob.isPresent());

			});
		});
	}

}
