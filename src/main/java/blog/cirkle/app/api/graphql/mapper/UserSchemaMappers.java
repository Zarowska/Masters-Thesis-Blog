package blog.cirkle.app.api.graphql.mapper;

import blog.cirkle.app.api.graphql.model.post.Post;
import blog.cirkle.app.api.graphql.model.post.PostPage;
import blog.cirkle.app.api.graphql.model.relation.RequestPage;
import blog.cirkle.app.api.graphql.model.user.User;
import blog.cirkle.app.api.graphql.model.user.UserPage;
import blog.cirkle.app.api.graphql.model.user.UserProfile;
import blog.cirkle.app.facade.GraphQlFacade;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class UserSchemaMappers {

	private final GraphQlFacade facade;

	@SchemaMapping(field = "profile", typeName = "User")
	public UserProfile getProfile(User user) {
		return facade.getProfileByUserId(user.getId());
	}

	@SchemaMapping(field = "friends", typeName = "User")
	public UserPage friendsByUser(User user, @Argument Integer page, @Argument Integer size) {
		return facade.friendsByUserId(user.getId(), PageRequest.of(page, size));
	}

	@SchemaMapping(field = "followers", typeName = "User")
	UserPage followersByUser(User user, @Argument Integer page, @Argument Integer size) {
		return facade.followersByUserId(user.getId(), PageRequest.of(page, size));
	}

	@SchemaMapping(field = "requests", typeName = "User")
	RequestPage requestsByUser(User user, @Argument Integer page, @Argument Integer size) {
		return facade.requestsByUserId(user.getId(), PageRequest.of(page, size));
	}

	@SchemaMapping(field = "post", typeName = "User")
	Post postByUserAndId(User user, @Argument UUID postId) {
		return facade.postByUserIdAndPostId(user.getId(), postId);
	}

	@SchemaMapping(field = "posts", typeName = "User")
	PostPage postsByUser(User user, @Argument Integer page, @Argument Integer size) {
		return facade.postsByUserId(user.getId(), PageRequest.of(page, size));
	}
}
