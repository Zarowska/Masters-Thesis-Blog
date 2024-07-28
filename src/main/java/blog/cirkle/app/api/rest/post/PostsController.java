package blog.cirkle.app.api.rest.post;

import blog.cirkle.app.api.rest.model.*;
import blog.cirkle.app.api.rest.model.request.*;
import blog.cirkle.app.facade.PostsFacade;
import io.swagger.v3.oas.annotations.Operation;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/v1/posts")
@RequiredArgsConstructor
public class PostsController {

	private final PostsFacade postsFacade;

	@Operation(summary = "Create a post", description = "Create a new post by providing required details", operationId = "createPost", tags = {
			"posts"})
	@PostMapping
	PostDto createPost(@RequestBody CreatePostDto request) {
		return postsFacade.createPost(request);
	}

	@Operation(summary = "Update a post", description = "Update an existing post by providing the post ID and update details", operationId = "updatePost", tags = {
			"posts"})
	@PutMapping(path = "/{postId}")
	PostDto updatePostByPostId(@PathVariable UUID postId, @RequestBody UpdateMediaDto request) {
		return postsFacade.updatePost(postId, request);
	}

	@Operation(summary = "Get a post", description = "Retrieve a post by its ID", operationId = "getPost", tags = {
			"posts"})
	@GetMapping(path = "/{postId}")
	PostDto getPostByPostId(@PathVariable UUID postId) {
		return postsFacade.getPostById(postId);
	}

	@Operation(summary = "Delete a post", description = "Delete a post by its ID", operationId = "deletePost", tags = {
			"posts"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping(path = "/{postId}")
	void deletePostByPostId(@PathVariable UUID postId) {
		postsFacade.deletePostById(postId);
	}
}
