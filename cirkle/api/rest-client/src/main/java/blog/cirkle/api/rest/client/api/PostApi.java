package blog.cirkle.api.rest.client.api;

import blog.cirkle.api.rest.client.model.PaginatedResponse;
import blog.cirkle.domain.model.request.CreatePostRequest;
import blog.cirkle.domain.model.response.PostDto;
import java.util.UUID;
import retrofit2.Call;
import retrofit2.http.*;

public interface PostApi extends Api {
	@GET("/api/v1/users/{userId}/posts")
	Call<PaginatedResponse<PostDto>> listByUserId(@Path("userId") UUID id);

	@POST("/api/v1/users/{userId}/posts")
	Call<PostDto> create(@Path("userId") UUID id, @Body CreatePostRequest post);

	@GET("/api/v1/users/{userId}/posts/{postId}")
	Call<PostDto> findByUserId(@Path("userId") UUID userId, @Path("postId") UUID postId);

	@DELETE("/api/v1/users/{userId}/posts/{postId}")
	Call<Void> deleteByUserId(@Path("userId") UUID userId, @Path("postId") UUID postId);

}
