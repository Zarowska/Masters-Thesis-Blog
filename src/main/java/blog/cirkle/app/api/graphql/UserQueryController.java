package blog.cirkle.app.api.graphql;

import blog.cirkle.app.api.graphql.model.User;
import java.util.List;
import java.util.UUID;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class UserQueryController {

	@QueryMapping(name = "user")
	public User getUser(@Argument UUID id) {
		return new User(UUID.randomUUID(), "test", "url", false);
	}

	@QueryMapping(name = "users")
	public List<User> getUsers(@Argument Integer limit, @Argument Integer offset) {
		return List.of(new User(UUID.randomUUID(), "test", "url", false),
				new User(UUID.randomUUID(), "test", "url", false), new User(UUID.randomUUID(), "test", "url", false));
	}
}
