package blog.cirkle.app.api.graphql.client.endpoints;

import static blog.cirkle.app.api.graphql.client.dsl.DSL.*;
import static java.util.Collections.*;

import blog.cirkle.app.api.graphql.client.GraphQlClient;
import blog.cirkle.app.api.graphql.client.dsl.Operation;
import blog.cirkle.app.api.graphql.model.user.User;
import blog.cirkle.app.api.graphql.model.user.UserPage;
import java.util.Map;
import java.util.UUID;
import org.springframework.graphql.data.method.annotation.Argument;

public class UserEndpoint extends AbstractEndpoint {

	private static Operation getCurrentUserInfo = operation("getCurrentUserInfo").withFields("id", "name");

	private static Operation getUser = operation("getUser").withArgument(stringOp("userId")).withFields("id", "name");

	private static Operation listUsers = operation("listUsers")
			.withArguments(stringOp("query"), intOp("page"), intOp("size")).withField(operation("pageInfo")
					.withFields("totalElements", "totalPages", "pageNumber", "pageSize", "first", "last"))
			.withField(operation("content").withFields("id", "name"));

	public UserEndpoint(GraphQlClient client) {
		super(client);
	}

	public User getCurrentUserInfo() {
		return doSingleQuery(emptyMap(), getCurrentUserInfo, User.class);
	}

	public User getUser(UUID userId) {
		return doSingleQuery(Map.of("userId", userId), getCurrentUserInfo, User.class);
	}

	public UserPage listUsers(@Argument String query, @Argument Integer page, @Argument Integer size) {
		return doSingleQuery(Map.of("query", query, "page", page, "size", size), listUsers, UserPage.class);
	}

	// @MutationMapping
	// public UserProfile updateUserProfile(@Argument UpdateUserProfileInput input)
	// {
	// return facade.updateProfile(input);
	// }
	//
	// @MutationMapping
	// public Boolean acceptUserRequest(@Argument UUID requestId) {
	// return facade.acceptUserRequest(requestId);
	// }
	//
	// @MutationMapping
	// public Boolean rejectUserRequest(@Argument UUID requestId) {
	// return facade.rejectUserRequest(requestId);
	// }
	//

}
