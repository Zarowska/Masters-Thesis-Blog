package blog.cirkle.app.api.rest.client.api;

import blog.cirkle.app.api.rest.client.model.GraphQLRequest;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.*;

public interface GraphQLApi {
	@POST("/graphql")
	Call<JsonObject> query(@Body GraphQLRequest body);

}