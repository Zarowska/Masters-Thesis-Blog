package blog.cirkle.api.rest.client.api;

import blog.cirkle.api.rest.client.model.PaginatedResponse;
import blog.cirkle.domain.model.newModel.RelationDto;
import java.util.UUID;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UsersRelationsApi extends Api {
	@GET("/api/v1/users/{userId}/friends")
	Call<PaginatedResponse<RelationDto>> friends(@Path("userId") UUID id, @Query("page") Integer page,
			@Query("size") Integer size);

	@GET("/api/v1/users/{userId}/followers")
	Call<PaginatedResponse<RelationDto>> followers(@Path("userId") UUID id, @Query("page") Integer page,
			@Query("size") Integer size);

	@POST("/api/v1/users/{userId}/friend")
	Call<Void> friend(@Path("userId") UUID userId);

	@POST("/api/v1/users/{userId}/follow")
	Call<Void> follow(@Path("userId") UUID userId);

	@POST("/api/v1/users/{userId}/unfriend")
	Call<Void> unfriend(@Path("userId") UUID userId);

	@POST("/api/v1/users/{userId}/unfollow")
	Call<Void> unfollow(@Path("userId") UUID userId);
}
