package blog.cirkle.api.rest.controller;

import static blog.cirkle.api.rest.constants.RestApiConstants.API_ROOT;

import blog.cirkle.domain.facade.PostFacade;
import blog.cirkle.domain.model.request.CreatePostRequest;
import blog.cirkle.domain.model.response.PostDto;
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
@RequestMapping(API_ROOT + "/users/{userId}/posts")
@RequiredArgsConstructor
@SecurityRequirements({@SecurityRequirement(name = "Bearer Authentication")})
public class PostController {

	private final PostFacade postFacade;

	@GetMapping
	Page<PostDto> listPostsByUserId(@PathVariable final UUID userId, @PageableDefault final Pageable pageable) {
		return postFacade.listByUserId(userId, pageable);
	}

	@GetMapping(path = "/{postId}")
	PostDto findPostsByUserId(@PathVariable final UUID userId, @PathVariable final UUID postId) {
		return postFacade.findByUserId(userId, postId);
	}

	@PostMapping
	PostDto createPost(@PathVariable final UUID userId, @RequestBody final CreatePostRequest request) {
		return postFacade.createOne(userId, request);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping(path = "/{postId}")
	void deleteByUserId(@PathVariable final UUID userId, @PathVariable final UUID postId) {
		postFacade.deleteByUserId(userId, postId);
	}

}
