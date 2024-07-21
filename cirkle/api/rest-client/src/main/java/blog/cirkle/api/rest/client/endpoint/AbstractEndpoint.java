package blog.cirkle.api.rest.client.endpoint;

import blog.cirkle.api.rest.client.exception.ClientResponseException;
import blog.cirkle.api.rest.client.utils.ClientContext;
import com.google.gson.Gson;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import retrofit2.Call;
import retrofit2.Response;

@RequiredArgsConstructor
public abstract class AbstractEndpoint {

	protected final ClientContext context;

	static final <T> Response<T> call(Call<T> call) {
		try {
			Response<T> response = call.execute();
			if (response.isSuccessful()) {
				return response;
			} else {
				try {
					String json = new String(response.errorBody().bytes(), StandardCharsets.UTF_8);
					ProblemDetail details = new Gson().fromJson(json, ProblemDetail.class);
					throw new ClientResponseException(details);
				} catch (ClientResponseException e) {
					throw e;
				} catch (Exception e) {
					throw new ClientResponseException(ProblemDetail
							.forStatusAndDetail(HttpStatusCode.valueOf(response.code()), response.message()));
				}
			}
		} catch (IOException e) {
			throw new RuntimeException("Call execution failed", e);
		}

	}
}
