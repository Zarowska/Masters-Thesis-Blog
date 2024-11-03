package blog.cirkle.app.api.rest.client.api;

import blog.cirkle.app.api.rest.client.model.PaginatedResponse;
import blog.cirkle.app.api.rest.model.*;
import blog.cirkle.app.api.rest.model.request.UpdateUserProfileDto;
import java.util.Map;
import java.util.UUID;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface UserApi {
	@POST("/api/v1/user/requests/{requestId}")
	Call<Void> acceptParticipantRequest(@Path("requestId") UUID requestId);

	@DELETE("/api/v1/user/requests/{requestId}")
	Call<Void> rejectParticipantRequest(@Path("requestId") UUID requestId);

	@GET("/api/v1/user/images")
	Call<PaginatedResponse<ImageDto>> listUserImages(@QueryMap Map<String, String> pageable);

	@Multipart
	@POST("/api/v1/user/images")
	Call<ImageDto> uploadImage(@Part MultipartBody.Part file);

	@GET("/api/v1/user")
	Call<ParticipantDto> getCurrentUserInfo();

	@GET("/api/v1/user/requests")
	Call<PaginatedResponse<RequestDto>> listUserRequests(@QueryMap Map<String, String> pageable);

	@GET("/api/v1/user/posts")
	Call<PaginatedResponse<PostDto>> listUserPosts(@QueryMap Map<String, String> pageable);

	@GET("/api/v1/user/feed")
	Call<PaginatedResponse<PostDto>> getUserFeed(@QueryMap Map<String, String> pageable);

	@GET("/api/v1/user/followers")
	Call<PaginatedResponse<ParticipantDto>> listFollowers(@QueryMap Map<String, String> pageable);

	@GET("/api/v1/user/friends")
	Call<PaginatedResponse<ParticipantDto>> listFriends(@QueryMap Map<String, String> pageable);

	@POST("/api/v1/user/profile")
	Call<UserProfileDto> updateProfile(@Body UpdateUserProfileDto userProfileDto);
}
