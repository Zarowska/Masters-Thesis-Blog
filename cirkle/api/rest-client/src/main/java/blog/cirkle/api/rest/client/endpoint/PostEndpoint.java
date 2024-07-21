package blog.cirkle.api.rest.client.endpoint;

import blog.cirkle.api.rest.client.api.PostApi;
import blog.cirkle.api.rest.client.model.PaginatedResponse;
import blog.cirkle.api.rest.client.utils.ClientContext;
import blog.cirkle.domain.model.request.CreatePostRequest;
import blog.cirkle.domain.model.response.PostDto;
import java.io.IOException;
import java.util.UUID;
import retrofit2.Response;

public class PostEndpoint extends AbstractEndpoint {

	private final PostApi api;

	public PostEndpoint(ClientContext context) {
		super(context);
		this.api = context.createApi(PostApi.class);
	}

	public PostDto create(UUID userId, CreatePostRequest request) throws IOException {
		Response<PostDto> response = call(api.create(userId, request));
		return response.body();
	}

	public PaginatedResponse<PostDto> listByUserId(UUID userId) {
		return call(api.listByUserId(userId)).body();
	}

	public PostDto findByUserId(UUID userId, UUID postId) {
		Response<PostDto> response = call(api.findByUserId(userId, postId));
		return response.body();
	}

	public void deleteById(UUID userId, UUID postId) {
		call(api.deleteByUserId(userId, postId));
	}
}
