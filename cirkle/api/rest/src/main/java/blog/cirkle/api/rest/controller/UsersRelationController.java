package blog.cirkle.api.rest.controller;

import static blog.cirkle.api.rest.constants.RestApiConstants.API_ROOT;

import blog.cirkle.domain.facade.UserFacade;
import blog.cirkle.domain.model.response.RelationDto;
import blog.cirkle.domain.model.response.RelationType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(API_ROOT + "/users/{userId}")
@RequiredArgsConstructor
@SecurityRequirements({@SecurityRequirement(name = "Bearer Authentication")})
public class UsersRelationController {

	private final UserFacade userFacade;

	@GetMapping(path = "/friends")
	Page<RelationDto> friends(@PathVariable final UUID userId, @PageableDefault final Pageable pageable) {
		return userFacade.findRelationsByType(userId, Set.of(RelationType.FRIEND), pageable);
	}

	@GetMapping(path = "/follows")
	Page<RelationDto> follows(@PathVariable final UUID userId, @PageableDefault final Pageable pageable) {
		return userFacade.findRelationsByType(userId, Set.of(RelationType.FOLLOWED), pageable);
	}

	@GetMapping(path = "/followers")
	Page<RelationDto> followers(@PathVariable final UUID userId, @PageableDefault final Pageable pageable) {
		return userFacade.findRelationsByType(userId, Set.of(RelationType.FOLLOWER), pageable);
	}

	@PostMapping(path = "/follow")
	void follow(@PathVariable final UUID userId) {
		userFacade.createRelation(userId, RelationType.FOLLOWED);
	}

	@PostMapping(path = "/friend")
	void friend(@PathVariable final UUID userId) {
		userFacade.createRelation(userId, RelationType.FRIEND);
	}

	@PostMapping(path = "/block")
	void block(@PathVariable final UUID userId) {
		userFacade.createRelation(userId, RelationType.BLOCKED);
	}

	@PostMapping(path = "/unfollow")
	void unfollow(@PathVariable final UUID userId) {
		userFacade.removeRelation(userId, RelationType.FOLLOWER);
	}

	@PostMapping(path = "/unfriend")
	void unfriend(@PathVariable final UUID userId) {
		userFacade.removeRelation(userId, RelationType.FRIEND);
	}

	@PostMapping(path = "/unblock")
	void unblock(@PathVariable final UUID userId) {
		userFacade.removeRelation(userId, RelationType.BLOCKED);
	}
}
