package blog.cirkle.app.api.graphql;

import blog.cirkle.app.facade.GraphQlFacade;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller("GraphQLRelationshipController")
@RequiredArgsConstructor
public class RelationshipController {

	private final GraphQlFacade facade;

	@MutationMapping
	public Boolean followUser(@Argument UUID userId) {
		return facade.followUser(userId);
	}

	@MutationMapping
	public Boolean unfollowUser(@Argument UUID userId) {
		return facade.unFollowUser(userId);
	}

	@MutationMapping
	public Boolean friendUser(@Argument UUID userId) {
		return facade.friendUser(userId);
	}

	@MutationMapping
	public Boolean unfriendUser(@Argument UUID userId) {
		return facade.unFriendUser(userId);
	}
}
