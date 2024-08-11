package blog.cirkle.app.api.graphql.resolvers;

import blog.cirkle.app.api.graphql.model.User;
import blog.cirkle.app.facade.UserFacade;
import graphql.kickstart.tools.GraphQLResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserResolver implements GraphQLResolver<User> {

	private final UserFacade userFacade;

	String getName(User user) {
		return userFacade.findByUserId(user.getId()).getName();
	}

	String getAvatarUrl(User user) {
		return userFacade.findByUserId(user.getId()).getAvatarUrl();
	}

	Boolean getIsGroup(User user) {
		return userFacade.findByUserId(user.getId()).getIsGroup();
	}

}
