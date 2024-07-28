package blog.cirkle.app.api.rest.post;

import blog.cirkle.app.api.rest.model.CommentDto;
import blog.cirkle.app.api.rest.model.request.*;
import blog.cirkle.app.facade.PostsFacade;
import io.swagger.v3.oas.annotations.Operation;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/v1/posts")
@RequiredArgsConstructor
public class PostCommentsController {

	private final PostsFacade postsFacade;

	@Operation(summary = "Create Post Comment", description = "Create a new comment in a given post", operationId = "createPostComment", tags = {
			"posts"})
	@PostMapping(path = "/{postId}/comments")
	CommentDto createCommentByPostId(@PathVariable UUID postId, @RequestBody CreateCommentDto request) {
		return postsFacade.createComment(postId, null, request);
	}

	@Operation(summary = "Create Nested Comment", description = "Create a nested comment in an existing comment of a given post", operationId = "createNestedComment", tags = {
			"posts"})
	@PostMapping(path = "/{postId}/comments/{commentId}/comments")
	CommentDto createCommentByPostIdAndCommentId(@PathVariable UUID postId, @PathVariable Long commentId,
			@RequestBody CreateCommentDto request) {
		return postsFacade.createComment(postId, commentId, request);
	}

	@Operation(summary = "List Root Comments", description = "Fetch a list of root-level comments for a given post", operationId = "listRootComments", tags = {
			"posts"})
	@GetMapping(path = "/{postId}/comments")
	Page<CommentDto> listRootCommentsByPostId(@PathVariable UUID postId, @PageableDefault Pageable pageable) {
		return postsFacade.listRootCommentsByPostId(postId, pageable);
	}

	@Operation(summary = "List Comment Replies", description = "Fetch a list of replies to a given comment in a given post", operationId = "listCommentReplies", tags = {
			"posts"})
	@GetMapping(path = "/{postId}/comments/{commentId}/comments")
	Page<CommentDto> listCommentsByPostIdAndCommentId(@PathVariable UUID postId, @PathVariable Long commentId,
			@PageableDefault Pageable pageable) {
		return postsFacade.listCommentsByPostIdAndCommentId(postId, commentId, pageable);
	}

	@Operation(summary = "Update Comment", description = "Update text or images in an existing comment of a given post", operationId = "updateComment", tags = {
			"posts"})
	@PutMapping(path = "/{postId}/comments/{commentId}")
	CommentDto updateCommentByPostIdAndCommentId(@PathVariable UUID postId, @PathVariable Long commentId,
			@RequestBody UpdateCommentDto request) {
		return postsFacade.updateCommentByPostIdAndCommentId(postId, commentId, request);
	}

	@Operation(summary = "Get Comment", description = "Fetch a specific comment details of a given post", operationId = "getComment", tags = {
			"posts"})
	@GetMapping(path = "/{postId}/comments/{commentId}")
	CommentDto getCommentByPostIdAndCommentId(@PathVariable UUID postId, @PathVariable Long commentId) {
		return postsFacade.getCommentByPostIdAndCommentId(postId, commentId);
	}

	@Operation(summary = "Delete Comment", description = "Delete a specific comment from a given post", operationId = "deleteComment", tags = {
			"posts"})
	@DeleteMapping(path = "/{postId}/comments/{commentId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void deleteCommentByPostIdAndCommentId(@PathVariable UUID postId, @PathVariable Long commentId) {
		postsFacade.deleteCommentByPostIdAndCommentId(postId, commentId);
	}
}
