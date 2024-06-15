package blog.cirkle.api.rest.client.api;

import blog.cirkle.api.rest.client.model.PaginatedResponse;
import blog.cirkle.domain.model.newModel.RelationRequestDto;
import blog.cirkle.domain.model.newModel.UserDto;
import java.util.UUID;
import retrofit2.Call;
import retrofit2.http.*;

public interface UserApi extends Api {
	@GET("/api/v1/user")
	Call<UserDto> currentUser();

	@GET("/api/v1/user/requests")
	Call<PaginatedResponse<RelationRequestDto>> requests(@Query("page") Integer page, @Query("size") Integer size);

	@POST("/api/v1/user/requests/{requestId}")
	Call<Void> acceptRequest(@Path("requestId") UUID requestId);

	@DELETE("/api/v1/user/requests/{requestId}")
	Call<Void> rejectRequest(@Path("requestId") UUID requestId);
}
