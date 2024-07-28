package blog.cirkle.app.api.rest.client.endpoints;

import blog.cirkle.app.api.rest.client.ApiClient;
import blog.cirkle.app.api.rest.client.api.ImagesApi;
import java.util.UUID;
import okhttp3.ResponseBody;

public class ImagesEndpoint extends AbstractEndpoint<ImagesApi> {
	public ImagesEndpoint(ApiClient.ClientContext context) {
		super(context, ImagesApi.class);
	}

	public ResponseBody getImageByID(UUID imageId) {
		return call(api.getImageByID(imageId)).body();
	}
}