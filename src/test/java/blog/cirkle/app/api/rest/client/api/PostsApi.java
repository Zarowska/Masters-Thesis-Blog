package blog.cirkle.app.api.rest.client.api;

import blog.cirkle.app.api.rest.client.model.PaginatedResponse;
import blog.cirkle.app.api.rest.model.CommentDto;
import blog.cirkle.app.api.rest.model.PostDto;
import blog.cirkle.app.api.rest.model.ReactionsDto;
import blog.cirkle.app.api.rest.model.request.*;
import java.util.UUID;
import retrofit2.Call;
import retrofit2.http.*;

public interface PostsApi {
	@GET("/api/v1/posts/{postId}")
	Call<PostDto> getPost(@Path("postId") UUID postId);

	@PUT("/api/v1/posts/{postId}")
	Call<PostDto> updatePost(@Path("postId") UUID postId, @Body UpdateMediaDto updatePostDto);

	@DELETE("/api/v1/posts/{postId}")
	Call<Void> deletePost(@Path("postId") UUID postId);

	@PUT("/api/v1/posts/{postId}/comments/{commentId}")
	Call<CommentDto> updateComment(@Path("postId") UUID postId, @Path("commentId") Long commentId,
			@Body UpdateCommentDto updateCommentDto);

	@DELETE("/api/v1/posts/{postId}/comments/{commentId}")
	Call<Void> deleteComment(@Path("postId") UUID postId, @Path("commentId") Long commentId);

	@POST("/api/v1/posts")
	Call<PostDto> createPost(@Body CreatePostDto createPostDto);

	@POST("/api/v1/posts/{postId}/reactions")
	Call<ReactionsDto> addReactionToPost(@Path("postId") UUID postId, @Body CreateReactionDto createReactionDto);

	@POST("/api/v1/posts/{postId}/images/{imageId}/reactions")
	Call<ReactionsDto> addReactionToImage(@Path("postId") UUID postId, @Path("imageId") UUID imageId,
			@Body CreateReactionDto createReactionDto);

	@GET("/api/v1/posts/{postId}/comments")
	Call<PaginatedResponse<CommentDto>> getComments(@Path("postId") UUID postId);

	@GET("/api/v1/posts/{postId}/comments/{commentId}/comments")
	Call<PaginatedResponse<CommentDto>> getComments(@Path("postId") UUID postId, @Path("commentId") Long commentId);

	@POST("/api/v1/posts/{postId}/comments")
	Call<CommentDto> createCommentOnPost(@Path("postId") UUID postId, @Body CreateCommentDto createCommentDto);

	@POST("/api/v1/posts/{postId}/comments/{commentId}/reactions")
	Call<ReactionsDto> addReactionToComment(@Path("postId") UUID postId, @Path("commentId") Long commentId,
			@Body CreateReactionDto createReactionDto);

	@POST("/api/v1/posts/{postId}/comments/{commentId}/comments")
	Call<CommentDto> createNestedComment(@Path("postId") UUID postId, @Path("commentId") Long commentId,
			@Body CreateCommentDto createCommentDto);

	@GET("/api/v1/posts/{postId}/comments/{commentId}")
	Call<CommentDto> getCommentByPostIdAndCommentId(@Path("postId") UUID postId, @Path("commentId") Long commentId);
}
