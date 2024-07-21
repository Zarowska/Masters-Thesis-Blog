package blog.cirkle.api.rest.client;

import blog.cirkle.api.rest.client.endpoint.*;
import blog.cirkle.api.rest.client.utils.AuthInterceptor;
import blog.cirkle.api.rest.client.utils.ClientContext;
import blog.cirkle.api.rest.client.utils.Lazy;
import blog.cirkle.domain.model.UserDto;
import java.util.UUID;
import java.util.function.Supplier;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Slf4j
public class ApiClient {
	@Getter(AccessLevel.PRIVATE)
	private final ClientContext context;

	private final Lazy<AuthEndpoint> auth = lazy(() -> new AuthEndpoint(getContext()));
	private final Lazy<RegisterEndpoint> registration = lazy(() -> new RegisterEndpoint(getContext()));
	private final Lazy<UserEndpoint> user = lazy(() -> new UserEndpoint(getContext()));
	private final Lazy<UsersEndpoint> users = lazy(() -> new UsersEndpoint(getContext()));
	private final Lazy<FilesEndpoint> files = lazy(() -> new FilesEndpoint(getContext()));
	private final Lazy<PostEndpoint> posts = lazy(() -> new PostEndpoint(getContext()));
	private final Lazy<CommentEndpoint> comments = lazy(() -> new CommentEndpoint(getContext()));
	private final Lazy<UUID> id = lazy(() -> getCurrentUser().getId());

	public ApiClient(String baseUrl) {
		OkHttpClient client = new OkHttpClient.Builder()
				.addInterceptor(new AuthInterceptor(() -> getContext().getToken())).build();
		Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).client(client)
				.addConverterFactory(GsonConverterFactory.create()).build();
		context = new ClientContext(retrofit);
	}

	public UserDto getCurrentUser() {
		return user().current();
	}

	public UUID getId() {
		return id.get();
	}

	public AuthEndpoint auth() {
		return auth.get();
	}

	public RegisterEndpoint registration() {
		return registration.get();
	}

	public UserEndpoint user() {
		return user.get();
	}

	public UserById user(UUID userId) {
		return new UserById(getContext(), userId);
	}

	public UsersEndpoint users() {
		return users.get();
	}

	public FilesEndpoint files() {
		return files.get();
	}

	public PostEndpoint posts() {
		return posts.get();
	}

	public CommentEndpoint comments() {
		return comments.get();
	}

	static final <T> Lazy<T> lazy(Supplier<T> builder) {
		return new Lazy<>(builder);
	}
}
