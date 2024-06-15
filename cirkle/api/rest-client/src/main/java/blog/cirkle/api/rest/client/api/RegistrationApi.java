package blog.cirkle.api.rest.client.api;

import blog.cirkle.domain.model.newModel.UserDto;
import blog.cirkle.domain.model.request.EmailValidationRequest;
import blog.cirkle.domain.model.request.RegistrationRequest;
import blog.cirkle.domain.model.response.AuthenticateResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegistrationApi extends Api {
	@POST("/api/v1/registration")
	Call<UserDto> authenticate(@Body RegistrationRequest request);

	@POST("/api/v1/registration/email-validation")
	Call<AuthenticateResponse> validateEmail(@Body EmailValidationRequest request);
}
