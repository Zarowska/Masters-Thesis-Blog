package blog.cirkle.app.api.graphql;

import blog.cirkle.app.api.graphql.model.user.UpdateUserProfileInput;
import blog.cirkle.app.api.graphql.model.user.User;
import blog.cirkle.app.api.graphql.model.user.UserPage;
import blog.cirkle.app.api.graphql.model.user.UserProfile;
import blog.cirkle.app.facade.GraphQlFacade;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller("GraphQLUserController")
@RequiredArgsConstructor
public class UserController {

	private final GraphQlFacade facade;

	@QueryMapping
	public User getCurrentUserInfo() {
		return facade.getCurrentUserInfo();
	}

	@QueryMapping
	public User getUser(@Argument UUID userId) {
		return facade.getUserById(userId);
	}

	@QueryMapping
	public UserPage listUsers(@Argument String query, @Argument Integer page, @Argument Integer size) {
		return facade.listUsers(query, PageRequest.of(page, size));
	}

	@MutationMapping
	public UserProfile updateUserProfile(@Argument UpdateUserProfileInput input) {
		return facade.updateProfile(input);
	}

	@MutationMapping
	public Boolean acceptUserRequest(@Argument UUID requestId) {
		return facade.acceptUserRequest(requestId);
	}

	@MutationMapping
	public Boolean rejectUserRequest(@Argument UUID requestId) {
		return facade.rejectUserRequest(requestId);
	}
}
