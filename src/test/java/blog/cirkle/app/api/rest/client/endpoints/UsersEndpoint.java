package blog.cirkle.app.api.rest.client.endpoints;

import blog.cirkle.app.api.rest.client.ApiClient;
import blog.cirkle.app.api.rest.client.api.UsersApi;
import blog.cirkle.app.api.rest.client.model.Pageable;
import blog.cirkle.app.api.rest.client.model.PaginatedResponse;
import blog.cirkle.app.api.rest.model.ParticipantDto;
import blog.cirkle.app.api.rest.model.PostDto;
import blog.cirkle.app.api.rest.model.UserProfileDto;
import java.util.UUID;

public class UsersEndpoint extends AbstractEndpoint<UsersApi> {

	public UsersEndpoint(ApiClient.ClientContext context) {
		super(context, UsersApi.class);
	}

	public Void unfriendUser(UUID userId) {
		return call(api.unfriendUser(userId)).body();
	}

	public Void unfollowUser(UUID userId) {
		return call(api.unfollowUser(userId)).body();
	}

	public Void friendUser(UUID userId) {
		return call(api.friendUser(userId)).body();
	}

	public Void followUser(UUID userId) {
		return call(api.followUser(userId)).body();
	}

	public PaginatedResponse<ParticipantDto> findAllUsers(Pageable pageable) {
		return call(api.findAllUsers(pageable)).body();
	}

	public ParticipantDto findUserById(UUID userId) {
		return call(api.findUserById(userId)).body();
	}

	public UserProfileDto findUserProfileByUserId(UUID userId) {
		return call(api.findUserProfileByUserId(userId)).body();
	}

	public PaginatedResponse<PostDto> listPostsByUserId(UUID userId, Pageable pageable) {
		return call(api.findPostsByUserId(userId, pageable)).body();
	}

	public UserProfileDto getUserProfileById(UUID userId) {
		return call(api.findUserProfileByUserId(userId)).body();
	}
}
