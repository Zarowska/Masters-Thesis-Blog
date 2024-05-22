
package blog.cirkle.api.rest.client.endpoint;

import blog.cirkle.api.rest.client.api.RegistrationApi;
import blog.cirkle.api.rest.client.utils.ClientContext;
import blog.cirkle.domain.model.request.EmailValidationRequest;
import blog.cirkle.domain.model.request.RegistrationRequest;
import blog.cirkle.domain.model.response.AuthenticateResponse;
import blog.cirkle.domain.model.response.UserDto;
import lombok.extern.slf4j.Slf4j;
import retrofit2.Response;

@Slf4j
public class RegisterEndpoint extends AbstractEndpoint {

	private final RegistrationApi api;

	public RegisterEndpoint(ClientContext context) {
		super(context);
		this.api = context.createApi(RegistrationApi.class);
	}

	public void register(String email, String firstName, String lastName, String password) {
		context.clearToken();
		Response<UserDto> registrationResponse = call(api
				.authenticate(new RegistrationRequest().setEmail(email).setFirstName(firstName).setLastName(lastName)));
		log.debug("User {}/{} {} registered", email, firstName, lastName);
		String emailValidationToken = registrationResponse.headers().get("email-validation");
		Response<AuthenticateResponse> response = call(
				api.validateEmail(new EmailValidationRequest(password, emailValidationToken)));
		log.debug("User {}/{} {} email validated, password set", email, firstName, lastName);
		context.setToken(response.body().getToken());
	}

}
