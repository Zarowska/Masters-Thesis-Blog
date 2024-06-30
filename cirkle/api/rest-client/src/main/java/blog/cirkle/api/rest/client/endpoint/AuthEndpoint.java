package blog.cirkle.api.rest.client.endpoint;

import blog.cirkle.api.rest.client.api.AuthApi;
import blog.cirkle.api.rest.client.utils.ClientContext;
import blog.cirkle.domain.model.response.AuthenticateResponse;
import java.util.Base64;

public class AuthEndpoint extends AbstractEndpoint {

	private final AuthApi api;

	public AuthEndpoint(ClientContext context) {
		super(context);
		this.api = context.createApi(AuthApi.class);
	}

	public void login(String email, String password) {
		context.clearToken();
		String basicAuthToken = Base64.getEncoder().encodeToString((email + ":" + password).getBytes());
		AuthenticateResponse response = call(api.authenticate("Basic " + basicAuthToken)).body();
		context.setToken(response.getToken());
	}

	public void loginForm(String email, String password, String token) {
		context.clearToken();
		AuthenticateResponse response = call(api.authenticateForm(email, password, token)).body();
		context.setToken(response.getToken());
	}
}
