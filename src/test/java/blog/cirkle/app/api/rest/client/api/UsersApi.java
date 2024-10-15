package blog.cirkle.app.api.rest.client.api;

import blog.cirkle.app.api.rest.client.model.PaginatedResponse;
import blog.cirkle.app.api.rest.model.ParticipantDto;
import blog.cirkle.app.api.rest.model.PostDto;
import blog.cirkle.app.api.rest.model.UserProfileDto;
import java.util.Map;
import java.util.UUID;
import retrofit2.Call;
import retrofit2.http.*;

public interface UsersApi {
	@POST("/api/v1/users/{userId}/unfriend")
	Call<Void> unfriendUser(@Path("userId") UUID userId);

	@POST("/api/v1/users/{userId}/unfollow")
	Call<Void> unfollowUser(@Path("userId") UUID userId);

	@POST("/api/v1/users/{userId}/friend")
	Call<Void> friendUser(@Path("userId") UUID userId);

	@POST("/api/v1/users/{userId}/follow")
	Call<Void> followUser(@Path("userId") UUID userId);

	@GET("/api/v1/users")
	Call<PaginatedResponse<ParticipantDto>> findAllUsers(@QueryMap Map<String, String> pageable);

	@GET("/api/v1/users/{userId}")
	Call<ParticipantDto> findUserById(@Path("userId") UUID userId);

	@GET("/api/v1/users/{userId}/profile")
	Call<UserProfileDto> findUserProfileByUserId(@Path("userId") UUID userId);

	@GET("/api/v1/users/{userId}/posts")
	Call<PaginatedResponse<PostDto>> findPostsByUserId(@Path("userId") UUID userId,
			@QueryMap Map<String, String> pageable);
}
