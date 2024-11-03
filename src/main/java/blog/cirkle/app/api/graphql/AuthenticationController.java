package blog.cirkle.app.api.graphql;

import blog.cirkle.app.api.graphql.model.auth.AuthenticateResponse;
import blog.cirkle.app.api.graphql.model.auth.CreateUserInput;
import blog.cirkle.app.api.graphql.model.auth.NewUser;
import blog.cirkle.app.api.graphql.model.auth.ResetPasswordInput;
import blog.cirkle.app.facade.GraphQlFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller("GraphQLAuthenticationController")
@RequiredArgsConstructor
public class AuthenticationController {

	private final GraphQlFacade facade;

	@MutationMapping
	public NewUser registerUser(@Argument CreateUserInput input) {
		return facade.registerUser(input);
	}

	@MutationMapping
	public AuthenticateResponse resetPassword(@Argument ResetPasswordInput input) {
		return facade.resetPassword(input);
	}

	@MutationMapping
	public AuthenticateResponse authenticate(@Argument String username, @Argument String password) {
		return facade.authenticate(username, password);
	}
}
