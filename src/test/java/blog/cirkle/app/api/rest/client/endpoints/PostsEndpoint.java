package blog.cirkle.app.api.rest.client.endpoints;

import blog.cirkle.app.api.rest.client.ApiClient;
import blog.cirkle.app.api.rest.client.api.PostsApi;
import blog.cirkle.app.api.rest.client.model.PaginatedResponse;
import blog.cirkle.app.api.rest.model.CommentDto;
import blog.cirkle.app.api.rest.model.PostDto;
import blog.cirkle.app.api.rest.model.ReactionsDto;
import blog.cirkle.app.api.rest.model.request.*;
import java.util.UUID;

public class PostsEndpoint extends AbstractEndpoint<PostsApi> {
	public PostsEndpoint(ApiClient.ClientContext context) {
		super(context, PostsApi.class);
	}

	public PostDto getPost(UUID postId) {
		return call(api.getPost(postId)).body();
	}

	public PostDto updatePost(UUID postId, UpdateMediaDto updatePostDto) {
		return call(api.updatePost(postId, updatePostDto)).body();
	}

	public Void deletePost(UUID postId) {
		return call(api.deletePost(postId)).body();
	}

	public CommentDto updateComment(UUID postId, Long commentId, UpdateCommentDto updateCommentDto) {
		return call(api.updateComment(postId, commentId, updateCommentDto)).body();
	}

	public Void deleteComment(UUID postId, Long commentId) {
		return call(api.deleteComment(postId, commentId)).body();
	}

	public PostDto createPost(CreatePostDto createPostDto) {
		return call(api.createPost(createPostDto)).body();
	}

	public ReactionsDto addReactionToPost(UUID postId, CreateReactionDto createReactionDto) {
		return call(api.addReactionToPost(postId, createReactionDto)).body();
	}

	public ReactionsDto addReactionToImage(UUID postId, UUID imageId, CreateReactionDto createReactionDto) {
		return call(api.addReactionToImage(postId, imageId, createReactionDto)).body();
	}

	public PaginatedResponse<CommentDto> getComments(UUID postId) {
		return call(api.getComments(postId)).body();
	}

	public PaginatedResponse<CommentDto> getComments(UUID postId, Long parentCommentId) {
		return call(api.getComments(postId, parentCommentId)).body();
	}

	public CommentDto createCommentOnPost(UUID postId, CreateCommentDto createCommentDto) {
		return call(api.createCommentOnPost(postId, createCommentDto)).body();
	}

	public ReactionsDto addReactionToComment(UUID postId, Long commentId, CreateReactionDto createReactionDto) {
		return call(api.addReactionToComment(postId, commentId, createReactionDto)).body();
	}

	public CommentDto createNestedComment(UUID postId, Long commentId, CreateCommentDto createCommentDto) {
		return call(api.createNestedComment(postId, commentId, createCommentDto)).body();
	}

	public CommentDto getCommentByPostIdAndCommentId(UUID postId, Long commentId) {
		return call(api.getCommentByPostIdAndCommentId(postId, commentId)).body();
	}
}
