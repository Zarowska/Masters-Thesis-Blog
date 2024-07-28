package blog.cirkle.app.api.rest.client.api;

import blog.cirkle.app.api.rest.client.model.Pageable;
import blog.cirkle.app.api.rest.client.model.PaginatedResponse;
import blog.cirkle.app.api.rest.model.ImageDto;
import blog.cirkle.app.api.rest.model.ParticipantDto;
import blog.cirkle.app.api.rest.model.PostDto;
import blog.cirkle.app.api.rest.model.RequestDto;
import java.util.UUID;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserApi {
	@POST("/api/v1/user/requests/{requestId}")
	Call<Void> acceptParticipantRequest(@Path("requestId") UUID requestId);

	@DELETE("/api/v1/user/requests/{requestId}")
	Call<Void> rejectParticipantRequest(@Path("requestId") UUID requestId);

	@GET("/api/v1/user/images")
	Call<PaginatedResponse<ImageDto>> listUserImages(@Query("pageable") Pageable pageable);

	@Multipart
	@POST("/api/v1/user/images")
	Call<ImageDto> uploadImage(@Part MultipartBody.Part file);

	@GET("/api/v1/user")
	Call<ParticipantDto> getCurrentUserInfo();

	@GET("/api/v1/user/requests")
	Call<PaginatedResponse<RequestDto>> listUserRequests(@Query("pageable") Pageable pageable);

	@GET("/api/v1/user/posts")
	Call<PaginatedResponse<PostDto>> listUserPosts(@Query("pageable") Pageable pageable);

	@GET("/api/v1/user/feed")
	Call<PaginatedResponse<PostDto>> getUserFeed(@Query("pageable") Pageable pageable);

	@GET("/api/v1/user/followers")
	Call<PaginatedResponse<ParticipantDto>> listFollowers(@Query("pageable") Pageable pageable);

	@GET("/api/v1/user/friends")
	Call<PaginatedResponse<ParticipantDto>> listFriends(@Query("pageable") Pageable pageable);
}
