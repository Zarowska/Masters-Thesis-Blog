package blog.cirkle.api.rest.client.endpoint;

import blog.cirkle.api.rest.client.api.UsersRelationsApi;
import blog.cirkle.api.rest.client.model.PaginatedResponse;
import blog.cirkle.api.rest.client.utils.ClientContext;
import blog.cirkle.domain.model.newModel.RelationDto;
import java.util.UUID;

public class UserById extends AbstractEndpoint {

	private final UsersRelationsApi usersRelationsApi;
	private final UUID userId;

	public UserById(ClientContext context, UUID userId) {
		super(context);
		usersRelationsApi = context.createApi(UsersRelationsApi.class);
		this.userId = userId;
	}

	public PaginatedResponse<RelationDto> friends(Integer page, Integer size) {
		return call(usersRelationsApi.friends(userId, page, size)).body();
	}

	public PaginatedResponse<RelationDto> followers(Integer page, Integer size) {
		return call(usersRelationsApi.followers(userId, page, size)).body();
	}

	public void friend() {
		call(usersRelationsApi.friend(userId));
	}

	public void follow() {
		call(usersRelationsApi.follow(userId));
	}

	public void unfriend() {
		call(usersRelationsApi.unfriend(userId));
	}

	public void unfollow() {
		call(usersRelationsApi.unfollow(userId));
	}
}
