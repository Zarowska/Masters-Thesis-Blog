package blog.cirkle.app.api.rest.client.api;

import blog.cirkle.app.api.rest.model.AuthenticateResponse;
import blog.cirkle.app.api.rest.model.NewUserDto;
import blog.cirkle.app.api.rest.model.ResetPasswordDto;
import blog.cirkle.app.api.rest.model.request.CreateUserDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AuthApi {
	@POST("/public/api/register")
	Call<NewUserDto> register(@Body CreateUserDto createUserDto);

	@POST("/public/api/password/reset/{key}")
	Call<AuthenticateResponse> resetPassword(@Path("key") String key, @Body ResetPasswordDto resetPasswordDto);

	@POST("/public/api/auth")
	Call<AuthenticateResponse> auth();

	@FormUrlEncoded
	@POST("/public/api/auth/form")
	Call<AuthenticateResponse> authByForm(@Field("email") String email, @Field("password") String password,
			@Field("token") String token);

	@FormUrlEncoded
	@POST("/public/api/auth/basic")
	Call<AuthenticateResponse> authByBasic(@Field("username") String username, @Field("password") String password);
}