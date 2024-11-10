package blog.cirkle.app.api.graphql.client.endpoints;

import static blog.cirkle.app.api.graphql.client.dsl.DSL.*;
import static blog.cirkle.app.api.graphql.client.dsl.DSL.stringOp;

import blog.cirkle.app.api.graphql.client.GraphQlClient;
import blog.cirkle.app.api.graphql.client.dsl.Operation;
import blog.cirkle.app.api.graphql.model.auth.AuthenticateResponse;
import blog.cirkle.app.api.graphql.model.auth.NewUser;
import com.google.gson.JsonElement;
import java.util.Map;

public class AuthEndpoint extends AbstractEndpoint {

	public static Operation resetPassword = operation("resetPassword")
			.withArguments(objectOp("input").append(stringOp("passwordResetToken")).append(stringOp("password")))
			.withFields("token");

	public static Operation registerUser = operation("registerUser")
			.withArgument(objectOp("input").append(stringOp("email")).append(stringOp("fullName")))
			.withFields("id", "username", "fullName", "avatarUrl", "passwordResetId");

	public static Operation authenticate = operation("authenticateByBasic")
			.withArguments(stringOp("username"), stringOp("password")).withFields("token");

	public AuthEndpoint(GraphQlClient client) {
		super(client);
	}

	public NewUser register(String email, String fullName) {
		Map<Operation, JsonElement> results = client.doCall("mutation", Map.of("email", email, "fullName", fullName),
				registerUser);
		NewUser response = extract(results, registerUser, NewUser.class);
		client.getUserId().set(response.getId());
		return response;
	}

	public AuthenticateResponse resetPassword(String passwordResetToken, String password) {
		Map<Operation, JsonElement> results = client.doCall("mutation",
				Map.of("passwordResetToken", passwordResetToken, "password", password), resetPassword);
		AuthenticateResponse response = extract(results, resetPassword, AuthenticateResponse.class);
		client.getToken().set(response.getToken());
		return response;
	}

	public GraphQlClient login(String email, String password) {
		Map<Operation, JsonElement> results = client.doCall("mutation", Map.of("username", email, "password", password),
				authenticate);
		AuthenticateResponse response = extract(results, authenticate, AuthenticateResponse.class);
		client.getToken().set(response.getToken());
		return this.client;
	}

	public void registerAndResetPassword(String email, String fullName, String password) {
		NewUser user = register(email, fullName);
		AuthenticateResponse response = resetPassword(user.getPasswordResetId(), password);
	}

}
