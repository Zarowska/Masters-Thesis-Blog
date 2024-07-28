package blog.cirkle.app.api.rest.client.endpoints;

import blog.cirkle.app.api.rest.client.ApiClient;
import blog.cirkle.app.api.rest.client.api.AuthApi;
import blog.cirkle.app.api.rest.model.AuthenticateResponse;
import blog.cirkle.app.api.rest.model.NewUserDto;
import blog.cirkle.app.api.rest.model.ResetPasswordDto;
import blog.cirkle.app.api.rest.model.request.CreateUserDto;

public class AuthEndpoint extends AbstractEndpoint<AuthApi> {

	public AuthEndpoint(ApiClient.ClientContext context) {
		super(context, AuthApi.class);
	}

	public NewUserDto register(CreateUserDto createUserDto) {
		return call(api.register(createUserDto)).body();
	}

	public AuthenticateResponse resetPassword(String key, ResetPasswordDto resetPasswordDto) {
		return call(api.resetPassword(key, resetPasswordDto)).body();
	}

	public AuthenticateResponse auth() {
		return call(api.auth()).body();
	}

	public AuthenticateResponse authByForm(String email, String password, String token) {
		return call(api.authByForm(email, password, token)).body();
	}

	public AuthenticateResponse authByBasic(String username, String password) {
		return call(api.authByBasic(username, password)).body();
	}
}