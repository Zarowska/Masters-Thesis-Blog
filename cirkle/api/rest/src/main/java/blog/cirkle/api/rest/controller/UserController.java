package blog.cirkle.api.rest.controller;

import static blog.cirkle.api.rest.constants.RestApiConstants.API_ROOT;

import blog.cirkle.domain.facade.PostFacade;
import blog.cirkle.domain.facade.UserFacade;
import blog.cirkle.domain.model.UserDto;
import blog.cirkle.domain.model.response.PostDto;
import blog.cirkle.domain.model.response.RelationRequestDto;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping(API_ROOT + "/user")
@RequiredArgsConstructor
@SecurityRequirements({@SecurityRequirement(name = "Bearer Authentication")})
public class UserController {

	private final UserFacade userFacade;
	private final PostFacade postFacade;

	@Operation(summary = "Get current user", description = "Current authenticated user info", operationId = "getCurrentUser", tags = {
			"user"})
	@GetMapping
	UserDto getCurrentUser() {
		return userFacade.getCurrentUser();
	}

	@Operation(summary = "List friendship requests", operationId = "getFriendshipRequests", tags = {"user",
			"relations"})
	@GetMapping(path = "/requests")
	Page<RelationRequestDto> requests(@PageableDefault final Pageable pageable) {
		return userFacade.listUserRelationRequests(pageable);
	}

	@Operation(summary = "Accept friendship request by id", operationId = "acceptFriendshipRequests", tags = {"user",
			"relations"})
	@PostMapping(path = "/requests/{requestId}")
	@ResponseStatus(HttpStatus.OK)
	void acceptRequests(@PathVariable final UUID requestId) {
		userFacade.acceptUserRelationRequest(requestId);
	}

	@Operation(summary = "Reject friendship request by id", operationId = "rejectFriendshipRequests", tags = {"user",
			"relations"})
	@DeleteMapping(path = "/requests/{requestId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void rejectRequests(@PathVariable final UUID userId, @PathVariable final UUID requestId) {
		userFacade.rejectUserRelationRequest(requestId);
	}

	@GetMapping(path = "/feed")
	Page<PostDto> getFeed(@PageableDefault final Pageable pageable) {
		return postFacade.feed(pageable);
	}

}
