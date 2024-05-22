package blog.cirkle.api.rest.client.api;

import blog.cirkle.domain.model.response.AuthenticateResponse;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AuthApi extends Api {
	@POST("/api/v1/auth")
	Call<AuthenticateResponse> authenticate(@Header("Authorization") String authorization);
}
