package com.zarowska.cirkle.api;

import com.zarowska.cirkle.AbstractTest;
import com.zarowska.cirkle.utils.TestUserContext;
import org.junit.jupiter.api.BeforeEach;

public class ReactionsApiTest extends AbstractTest {

	// Get post's reaction
	// Add posts's reaction
	// Get post reaction by id
	// Delete post reaction by id

	// Add reaction to comment
	// Get comment's reaction by id
	// Delete comment's reaction
	TestUserContext testUserContext;

	@BeforeEach
	void setUp() {
		testUserContext = context("Bob Marley", "bob@marley.com", "http:/some/avatar");
		testUserContext.getApi().api().apiInfo(); // Simulating API call to setup user
	}

}
