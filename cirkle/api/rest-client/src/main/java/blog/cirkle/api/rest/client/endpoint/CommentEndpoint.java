package blog.cirkle.api.rest.client.endpoint;

import blog.cirkle.api.rest.client.api.CommentApi;
import blog.cirkle.api.rest.client.model.PaginatedResponse;
import blog.cirkle.api.rest.client.utils.ClientContext;
import blog.cirkle.domain.model.request.CreateCommentRequest;
import blog.cirkle.domain.model.request.UpdateCommentRequest;
import blog.cirkle.domain.model.response.CommentDto;
import java.util.UUID;
import retrofit2.Response;

public class CommentEndpoint extends AbstractEndpoint {

	private final CommentApi api;

	public CommentEndpoint(ClientContext context) {
		super(context);
		this.api = context.createApi(CommentApi.class);
	}

	public CommentDto addComment(UUID userId, UUID postId, CreateCommentRequest createRequest) {
		Response<CommentDto> response = call(api.createPostComment(userId, postId, createRequest));
		return response.body();
	}

	public CommentDto addComment(UUID userId, UUID postId, UUID parentCommentId, CreateCommentRequest createRequest) {
		Response<CommentDto> response = call(api.createPostComment(userId, postId, parentCommentId, createRequest));
		return response.body();
	}

	public CommentDto findById(UUID userId, UUID postId, UUID commentId) {
		Response<CommentDto> response = call(api.findById(userId, postId, commentId));
		return response.body();
	}

	public PaginatedResponse<CommentDto> findByPostId(UUID userId, UUID postId) {
		Response<PaginatedResponse<CommentDto>> response = call(api.findByPostId(userId, postId));
		return response.body();
	}

	public CommentDto updateComment(UUID userId, UUID postId, UUID commentId, UpdateCommentRequest updateRequest) {
		Response<CommentDto> response = call(api.updatePostComment(userId, postId, commentId, updateRequest));
		return response.body();
	}

	// public PostDto create(UUID userId, CreatePostRequest request) throws
	// IOException {
	// Response<PostDto> response = call(api.create(userId, request));
	// return response.body();
	// }
	//
	// public PaginatedResponse<PostDto> listByUserId(UUID userId) {
	// return call(api.listByUserId(userId)).body();
	// }
	//
	// public PostDto findByUserId(UUID userId, UUID postId) {
	// Response<PostDto> response = call(api.findByUserId(userId, postId));
	// return response.body();
	// }
	//
	// public void deleteById(UUID userId, UUID postId) {
	// call(api.deleteByUserId(userId, postId));
	// }
}
