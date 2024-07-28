package blog.cirkle.app.api.rest.client;

import blog.cirkle.app.api.rest.client.endpoints.*;
import blog.cirkle.app.api.rest.model.AuthenticateResponse;
import blog.cirkle.app.api.rest.model.NewUserDto;
import blog.cirkle.app.api.rest.model.ResetPasswordDto;
import blog.cirkle.app.api.rest.model.request.CreateUserDto;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.http.HttpHeaders;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
	public final AuthEndpoint auth;
	public final GroupsEndpoint groups;
	public final ImagesEndpoint images;
	public final MessagesEndpoint messages;
	public final PostsEndpoint posts;
	public final UserEndpoint user;
	public final UsersEndpoint users;
	private final ClientContext context;

	public ApiClient(String baseUrl) {
		context = new ClientContext(baseUrl);
		auth = new AuthEndpoint(context);
		groups = new GroupsEndpoint(context);
		images = new ImagesEndpoint(context);
		messages = new MessagesEndpoint(context);
		posts = new PostsEndpoint(context);
		user = new UserEndpoint(context);
		users = new UsersEndpoint(context);

	}

	public boolean isLoggedIn() {
		return context.tokenRef.get() != null;
	}

	public ApiClient register(CreateUserDto request, String password) {
		context.clear();
		NewUserDto preRegistration = auth.register(request);
		AuthenticateResponse authResponse = auth.resetPassword(preRegistration.getPasswordResetId(),
				new ResetPasswordDto(password));
		context.tokenRef.set(authResponse.getToken());
		return this;
	}

	public ApiClient login(String username, String password) {
		context.clear();
		AuthenticateResponse authResponse = auth.authByBasic(username, password);
		context.tokenRef.set(authResponse.getToken());
		return this;
	}

	public UUID getUserId() {
		if (context.userId.get() == null) {
			context.userId.set(user.getCurrentUserInfo().getId());
		}
		return context.userId.get();
	}

	public static class ClientContext {
		private final AtomicReference<String> tokenRef = new AtomicReference();
		private final AtomicReference<UUID> userId = new AtomicReference();

		private final Retrofit retrofit;

		public Optional<String> getToken() {
			return Optional.ofNullable(tokenRef.get());
		}

		public <T> T createApi(Class<T> api) {
			return retrofit.create(api);
		}

		public ClientContext(String baseUrl) {
			OkHttpClient client = new OkHttpClient.Builder().addInterceptor(chain -> {
				Request request = Optional.ofNullable(tokenRef.get()).map(token -> {
					Request originalRequest = chain.request();
					Request.Builder builder = originalRequest.newBuilder();
					builder.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
					return builder.build();
				}).orElseGet(chain::request);
				return chain.proceed(request);
			}).build();
			retrofit = new Retrofit.Builder().baseUrl(baseUrl).client(client)
					.addConverterFactory(GsonConverterFactory.create()).build();
		}

		public void clear() {
			tokenRef.set(null);
			userId.set(null);
		}
	}
}
