package blog.cirkle.api.rest.client.api;

import blog.cirkle.api.rest.client.model.PaginatedResponse;
import blog.cirkle.domain.model.request.CreatePostDto;
import blog.cirkle.domain.model.response.PostDto;
import java.util.UUID;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PostApi extends Api {
	@GET("/api/v1/users/{userId}/posts")
	Call<PaginatedResponse<PostDto>> findByUserId(@Path("userId") UUID id);

	@POST("/api/v1/users/{userId}/posts")
	Call<PostDto> create(@Path("userId") UUID id, @Body CreatePostDto post);
}
