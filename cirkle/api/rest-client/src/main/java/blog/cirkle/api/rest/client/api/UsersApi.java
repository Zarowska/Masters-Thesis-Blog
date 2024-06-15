package blog.cirkle.api.rest.client.api;

import blog.cirkle.api.rest.client.model.PaginatedResponse;
import blog.cirkle.domain.model.newModel.UserDto;
import java.util.UUID;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UsersApi extends Api {
	@GET("/api/v1/users")
	Call<PaginatedResponse<UserDto>> listUsers(@Query("page") Integer page, @Query("size") Integer size);

	@GET("/api/v1/users/{slug}/by-slug")
	Call<UserDto> findBySlug(@Path("slug") String slug);

	@GET("/api/v1/users/{id}")
	Call<UserDto> findById(@Path("id") UUID id);
}
