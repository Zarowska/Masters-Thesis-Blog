package blog.cirkle.api.rest.client.utils;

import blog.cirkle.api.rest.client.api.Api;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import lombok.RequiredArgsConstructor;
import retrofit2.Retrofit;

@RequiredArgsConstructor
public class ClientContext {
	private final AtomicReference<String> tokenRef = new AtomicReference();

	private final Retrofit retrofit;

	public void clearToken() {
		tokenRef.set(null);
	}

	public Optional<String> getToken() {
		return Optional.ofNullable(tokenRef.get());
	}

	public void setToken(String token) {
		tokenRef.set(token);
	}

	public <T extends Api> T createApi(Class<T> api) {
		return retrofit.create(api);
	}
}