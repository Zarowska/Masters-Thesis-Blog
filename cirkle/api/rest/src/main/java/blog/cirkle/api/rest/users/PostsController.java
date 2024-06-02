package blog.cirkle.api.rest.users;

import blog.cirkle.domain.facade.PostFacade;
import blog.cirkle.domain.model.request.CreatePostDto;
import blog.cirkle.domain.model.response.PostDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users/{userId}/posts")
@RequiredArgsConstructor
public class PostsController {

	private final PostFacade postFacade;

	@GetMapping
	Page<PostDto> findAllPostsByUserId(@PathVariable UUID userId, @PageableDefault Pageable pageable) {
		return postFacade.findByUserId(userId, pageable);
	}

	@PostMapping
	PostDto createPost(@PathVariable UUID userId, @RequestBody CreatePostDto request) {
		return postFacade.createOne(userId, request);
	}

}
