package blog.cirkle.api.rest.users;

import blog.cirkle.domain.facade.PostFacade;
import blog.cirkle.domain.model.CreatePostDto;
import blog.cirkle.domain.model.PostDto;
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

	@PostMapping
	PostDto create(@PathVariable UUID userId, @RequestBody CreatePostDto request) {
		return postFacade.createOne(userId, request);
	}

	@GetMapping
	Page<PostDto> findAllPosts(@PathVariable UUID userId, @PageableDefault Pageable pageable) {
		return postFacade.findByUserId(userId, pageable);
	}
}
