package blog.cirkle.api.rest.controller;

import static blog.cirkle.api.rest.constants.RestApiConstants.API_ROOT;

import blog.cirkle.domain.facade.PostFacade;
import blog.cirkle.domain.model.request.CreateCommentRequest;
import blog.cirkle.domain.model.request.UpdateCommentRequest;
import blog.cirkle.domain.model.response.CommentDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(API_ROOT + "/users/{userId}/posts/{postId}/comments")
@RequiredArgsConstructor
@SecurityRequirements({@SecurityRequirement(name = "Bearer Authentication")})
public class PostCommentController {

	private final PostFacade postFacade;

	@PostMapping
	CommentDto addComment(@PathVariable UUID userId, @PathVariable UUID postId,
			@RequestBody CreateCommentRequest commentDto) {
		return postFacade.addComment(userId, postId, commentDto);
	}

	@PostMapping(path = "/{commentId}")
	CommentDto addCommentToComment(@PathVariable UUID userId, @PathVariable UUID postId, @PathVariable UUID commentId,
			@RequestBody CreateCommentRequest commentDto) {
		return postFacade.addComment(userId, postId, commentId, commentDto);
	}

	@PutMapping(path = "/{commentId}")
	CommentDto updateComment(@PathVariable UUID userId, @PathVariable UUID postId, @PathVariable UUID commentId,
			@RequestBody UpdateCommentRequest commentDto) {
		return postFacade.updateComment(userId, postId, commentId, commentDto);
	}

	@GetMapping
	Page<CommentDto> listComments(@PathVariable UUID userId, @PathVariable UUID postId,
			@PageableDefault Pageable pageable) {
		return postFacade.listComments(userId, postId, pageable);
	}

	@GetMapping(path = "/{commentId}")
	CommentDto listComments(@PathVariable UUID userId, @PathVariable UUID postId, @PathVariable UUID commentId,
			@PageableDefault Pageable pageable) {
		return postFacade.findCommentById(userId, postId, commentId, pageable);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping(path = "/{commentId}")
	void deleteComment(@PathVariable UUID userId, @PathVariable UUID postId, @PathVariable UUID commentId) {
		postFacade.deleteComment(userId, postId, commentId);
	}

}
