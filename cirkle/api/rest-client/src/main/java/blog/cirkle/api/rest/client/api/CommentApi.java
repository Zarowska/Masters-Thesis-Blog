package blog.cirkle.api.rest.client.api;

import blog.cirkle.api.rest.client.model.PaginatedResponse;
import blog.cirkle.domain.model.request.CreateCommentRequest;
import blog.cirkle.domain.model.request.UpdateCommentRequest;
import blog.cirkle.domain.model.response.CommentDto;
import java.util.UUID;
import retrofit2.Call;
import retrofit2.http.*;

public interface CommentApi extends Api {
	@POST("/api/v1/users/{userId}/posts/{postId}/comments")
	Call<CommentDto> createPostComment(@Path("userId") UUID userId, @Path("postId") UUID postId,
			@Body CreateCommentRequest createRequest);

	@POST("/api/v1/users/{userId}/posts/{postId}/comments/{commentId}")
	Call<CommentDto> createPostComment(@Path("userId") UUID userId, @Path("postId") UUID postId,
			@Path("commentId") UUID commentId, @Body CreateCommentRequest createRequest);

	@GET("/api/v1/users/{userId}/posts/{postId}/comments/{commentId}")
	Call<CommentDto> findById(@Path("userId") UUID userId, @Path("postId") UUID postId,
			@Path("commentId") UUID commentId);

	@GET("/api/v1/users/{userId}/posts/{postId}/comments")
	Call<PaginatedResponse<CommentDto>> findByPostId(@Path("userId") UUID userId, @Path("postId") UUID postId);

	@PUT("/api/v1/users/{userId}/posts/{postId}/comments/{commentId}")
	Call<CommentDto> updatePostComment(@Path("userId") UUID userId, @Path("postId") UUID postId,
			@Path("commentId") UUID commentId, @Body UpdateCommentRequest updateRequest);

	// @GET("/api/v1/users/{userId}/posts")
	// Call<PaginatedResponse<PostDto>> listByUserId(@Path("userId") UUID id);
	//
	// @POST("/api/v1/users/{userId}/posts")
	// Call<PostDto> create(@Path("userId") UUID id, @Body CreatePostRequest post);
	//
	// @GET("/api/v1/users/{userId}/posts/{postId}")
	// Call<PostDto> findByUserId(@Path("userId") UUID userId, @Path("postId") UUID
	// postId);
	//
	// @DELETE("/api/v1/users/{userId}/posts/{postId}")
	// Call<Void> deleteByUserId(@Path("userId") UUID userId, @Path("postId") UUID
	// postId);

}
