package blog.cirkle.app.api.rest;

import blog.cirkle.app.api.rest.model.ParticipantDto;
import blog.cirkle.app.api.rest.model.PostDto;
import blog.cirkle.app.api.rest.model.UserProfileDto;
import blog.cirkle.app.facade.UserFacade;
import io.swagger.v3.oas.annotations.Operation;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/v1/users")
@RequiredArgsConstructor
public class UsersController {

	private final UserFacade userFacade;

	@GetMapping
	@Operation(summary = "View all users", description = "Get a paged list of all users", operationId = "findAllUsers", tags = {
			"users"})
	Page<ParticipantDto> findAll(@RequestParam(required = false) String query, @PageableDefault Pageable pageable) {
		return userFacade.findAll(query, pageable);
	}

	@GetMapping(path = "/{userId}")
	@Operation(summary = "View user by ID", description = "Get user details by their ID", operationId = "findUserById", tags = {
			"users"})
	ParticipantDto findByUserId(@PathVariable UUID userId) {
		return userFacade.findByUserId(userId);
	}

	@GetMapping(path = "/{userId}/profile")
	@Operation(summary = "View user profile by ID", description = "Get user profile details by their ID", operationId = "findUserProfileByUserId", tags = {
			"users"})
	UserProfileDto findProfileByUserId(@PathVariable UUID userId) {
		return userFacade.getProfileByUserId(userId);
	}

	@GetMapping(path = "/{userId}/posts")
	@Operation(summary = "View posts by user ID", description = "Get a paged list of posts by user ID", operationId = "findPostsByUserId", tags = {
			"users"})
	Page<PostDto> findPostsByUserId(@PathVariable UUID userId, @PageableDefault Pageable pageable) {
		return userFacade.findPostsByUserId(userId, pageable);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PostMapping(path = "/{userId}/follow")
	@Operation(summary = "Follow a user by ID", description = "Follow a user by their ID", operationId = "followUser", tags = {
			"users", "relations"})
	void follow(@PathVariable UUID userId) {
		userFacade.followUserByUserId(userId);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PostMapping(path = "/{userId}/unfollow")
	@Operation(summary = "Unfollow a user by ID", description = "Unfollow a user by their ID", operationId = "unfollowUser", tags = {
			"users", "relations"})
	void unfollow(@PathVariable UUID userId) {
		userFacade.unFollowUserByUserId(userId);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PostMapping(path = "/{userId}/friend")
	@Operation(summary = "Make a user friend by ID", description = "Make a user your friend by their ID", operationId = "friendUser", tags = {
			"users", "relations"})
	void friend(@PathVariable UUID userId) {
		userFacade.friendUserByUserId(userId);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PostMapping(path = "/{userId}/unfriend")
	@Operation(summary = "Remove friendship with a user by ID", description = "Remove friendship with a user by their ID", operationId = "unfriendUser", tags = {
			"users", "relations"})
	void unfriend(@PathVariable UUID userId) {
		userFacade.unFriendUserByUserId(userId);
	}
}
