package blog.cirkle.api.rest.controller;

import static blog.cirkle.api.rest.constants.RestApiConstants.API_ROOT;

import blog.cirkle.domain.facade.PostFacade;
import blog.cirkle.domain.model.request.CreatePostDto;
import blog.cirkle.domain.model.response.PostDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(API_ROOT + "/users/{userId}/posts")
@RequiredArgsConstructor
@SecurityRequirements({@SecurityRequirement(name = "Bearer Authentication")})
public class PostController {

	private final PostFacade postFacade;

	@GetMapping
	Page<PostDto> listPostsByUserId(@PathVariable final UUID userId, @PageableDefault final Pageable pageable) {
		return postFacade.findByUserId(userId, pageable);
	}

	@PostMapping
	PostDto createPost(@PathVariable final UUID userId, @RequestBody final CreatePostDto request) {
		return postFacade.createOne(userId, request);
	}

}
