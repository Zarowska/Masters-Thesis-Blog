package blog.cirkle.app.api.graphql;

import blog.cirkle.app.api.graphql.model.User;
import blog.cirkle.app.service.UserService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryResolver implements GraphQLQueryResolver {

	private final UserService userService;

	User user(UUID id) {
		blog.cirkle.app.model.entity.User user = userService.findById(id);
		return User.builder().id(user.getId()).build();
	}

	List<User> users(Integer limit, Integer offset) {
		Page<User> page = userService.findAll(null, Pageable.ofSize(limit).withPage(offset))
				.map(u -> User.builder().id(u.getId()).build());
		return page.getContent();
	}

}
