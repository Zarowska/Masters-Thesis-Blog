package blog.cirkle.api.rest.client.utils;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import okhttp3.Interceptor;
import okhttp3.Request;
import org.springframework.http.HttpHeaders;

@RequiredArgsConstructor
public class AuthInterceptor implements Interceptor {

	private final Supplier<Optional<String>> tokenSupplier;

	@Override
	public okhttp3.Response intercept(Chain chain) throws IOException {
		Request request = tokenSupplier.get().map(token -> {
			Request originalRequest = chain.request();
			Request.Builder builder = originalRequest.newBuilder();
			builder.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
			return builder.build();
		}).orElseGet(chain::request);
		return chain.proceed(request);
	}
}