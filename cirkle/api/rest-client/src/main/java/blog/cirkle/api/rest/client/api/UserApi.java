package blog.cirkle.api.rest.client.api;

import blog.cirkle.domain.model.response.UserDto;
import retrofit2.Call;
import retrofit2.http.GET;

public interface UserApi extends Api {
	@GET("/api/v1/user")
	Call<UserDto> currentUser();
}
