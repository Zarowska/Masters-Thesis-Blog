package blog.cirkle.app.api.rest.post;

import blog.cirkle.app.api.rest.model.ReactionsDto;
import blog.cirkle.app.api.rest.model.request.CreateReactionDto;
import blog.cirkle.app.facade.PostsFacade;
import io.swagger.v3.oas.annotations.Operation;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/v1/posts")
@RequiredArgsConstructor
public class PostsReactionsController {

	private final PostsFacade postsFacade;

	@Operation(summary = "Add a reaction", description = "Add a reaction to a post", operationId = "addReactionToPost", tags = {
			"posts"})
	@PostMapping(path = "/{postId}/reactions")
	ReactionsDto addReactionToPost(@PathVariable UUID postId, @RequestBody CreateReactionDto request) {
		return postsFacade.createReactionByPostId(postId, request);
	}

	@Operation(summary = "Add a reaction to image", description = "Add a reaction to an image in a post", operationId = "addReactionToImage", tags = {
			"posts"})
	@PostMapping(path = "/{postId}/images/{imageId}/reactions")
	ReactionsDto addReactionToPostImage(@PathVariable UUID postId, @PathVariable UUID imageId,
			@RequestBody CreateReactionDto request) {
		return postsFacade.createReactionByPostIdAndImageId(postId, imageId, request);
	}

	@Operation(summary = "Add a reaction to comment", description = "Add a reaction to a comment in a post", operationId = "addReactionToComment", tags = {
			"posts"})
	@PostMapping(path = "/{postId}/comments/{commentId}/reactions")
	ReactionsDto addReactionToComment(@PathVariable UUID postId, @PathVariable Long commentId,
			@RequestBody CreateReactionDto request) {
		return postsFacade.createReactionByPostIdAndCommentId(postId, commentId, request);
	}
}
