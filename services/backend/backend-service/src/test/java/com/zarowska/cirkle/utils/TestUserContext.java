package com.zarowska.cirkle.utils;

import com.zarowska.cirkle.api.client.BlogClient;
import java.util.UUID;
import lombok.Getter;

public class TestUserContext {

	@Getter
	private final BlogClient api;

	@Getter
	private UUID userId = null;

	public TestUserContext(BlogClient client) {
		this.api = client;
	}

	public void apply(UserContextAction action) throws Exception {
		action.consume(this);
	}

	public UUID getUserId() {
		if (userId == null) {
			userId = api.user().getCurrentUser().getId();
		}
		return userId;
	}

	public interface UserContextAction {
		void consume(TestUserContext ctx) throws Exception;
	}
}
