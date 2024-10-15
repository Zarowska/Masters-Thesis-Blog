package blog.cirkle.app.api.rest.client.endpoints;

import blog.cirkle.app.api.rest.client.ApiClient;
import blog.cirkle.app.api.rest.client.exception.ClientResponseException;
import com.google.gson.Gson;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import retrofit2.Call;
import retrofit2.Response;

public abstract class AbstractEndpoint<A> {

	protected final ApiClient.ClientContext context;
	protected final A api;

	public AbstractEndpoint(ApiClient.ClientContext context, Class<A> api) {
		this.context = context;
		this.api = context.createApi(api);
	}

	protected final <T> Response<T> call(Call<T> call) {
		try {
			Response<T> response = call.execute();
			if (response.isSuccessful()) {
				context.logRequest(call, response);

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