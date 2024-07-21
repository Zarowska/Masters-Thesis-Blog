package blog.cirkle.api.rest.controller;

import static blog.cirkle.api.rest.constants.RestApiConstants.API_ROOT;

import blog.cirkle.domain.facade.PostFacade;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(API_ROOT + "/users/{userId}/posts/{postId}/section/{sectionId}/reactions")
@RequiredArgsConstructor
@SecurityRequirements({@SecurityRequirement(name = "Bearer Authentication")})
public class PostSectionReactionController {

	private final PostFacade postFacade;

	// add reaction
	// delete reaction
	// list reactions

}
