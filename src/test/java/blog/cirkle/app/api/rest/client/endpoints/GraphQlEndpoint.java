package blog.cirkle.app.api.rest.client.endpoints;

import blog.cirkle.app.api.rest.client.ApiClient;
import blog.cirkle.app.api.rest.client.api.GraphQLApi;
import blog.cirkle.app.api.rest.client.model.GraphQLRequest;
import com.google.gson.JsonObject;

public class GraphQlEndpoint extends AbstractEndpoint<GraphQLApi> {
	public GraphQlEndpoint(ApiClient.ClientContext context) {
		super(context, GraphQLApi.class);
	}

	public JsonObject execute(String operationName, String query) {
		GraphQLRequest request = new GraphQLRequest(operationName, query);
		return call(api.query(request)).body();
	}

	public JsonObject execute(String query) {
		return execute("Undefined", query);
	}
}
