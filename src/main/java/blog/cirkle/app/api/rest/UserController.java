package blog.cirkle.app.api.rest;

import blog.cirkle.app.api.rest.model.ImageDto;
import blog.cirkle.app.api.rest.model.ParticipantDto;
import blog.cirkle.app.api.rest.model.PostDto;
import blog.cirkle.app.api.rest.model.RequestDto;
import blog.cirkle.app.facade.ImageFacade;
import blog.cirkle.app.facade.UserFacade;
import io.swagger.v3.oas.annotations.Operation;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/v1/user")
@RequiredArgsConstructor
public class UserController {

	private final UserFacade userFacade;
	private final ImageFacade imageFacade;

	@GetMapping
	@Operation(summary = "Get current user info", description = "Fetch the information of the currently logged in user", operationId = "getCurrentUserInfo", tags = {
			"user"})
	ParticipantDto getCurrentUserInfo() {
		return userFacade.getCurrentUserInfo();
	}

	@GetMapping(path = "/images")
	@Operation(summary = "List user images", description = "Fetch images uploaded by the current user", operationId = "listUserImages", tags = {
			"user"})
	Page<ImageDto> listUserImages(@PageableDefault Pageable pageable) {
		return imageFacade.listCurrentUserImages(pageable);
	}

	@PostMapping(path = "/images")
	@Operation(summary = "Upload image", description = "Upload new image by the current user", operationId = "uploadImage", tags = {
			"user"})
	ImageDto uploadImage(@RequestParam("image") MultipartFile file) {
		return imageFacade.uploadImage(file);
	}

	@GetMapping(path = "/posts")
	@Operation(summary = "List user posts", description = "Fetch posts by the current user", operationId = "listUserPosts", tags = {
			"user"})
	Page<PostDto> listUserPosts(Pageable pageable) {
		return userFacade.listCurrentUserPosts(pageable);
	}

	@GetMapping(path = "/followers")
	@Operation(summary = "List user followers", description = "Fetch user followers of the current user", operationId = "listUserFollowers", tags = {
			"user"})
	Page<ParticipantDto> listUserFollowers(Pageable pageable) {
		return userFacade.listCurrentUserFollowers(pageable);
	}

	@GetMapping(path = "/friends")
	@Operation(summary = "List user friends", description = "Fetch user friends of the current user", operationId = "listUserFriends", tags = {
			"user"})
	Page<ParticipantDto> listUserFriends(Pageable pageable) {
		return userFacade.listCurrentUserFriends(pageable);
	}

	@GetMapping(path = "/requests")
	@Operation(summary = "List user requests", description = "Fetch requests related to the current user", operationId = "listUserRequests", tags = {
			"user"})
	Page<RequestDto> listUserRequests(Pageable pageable) {
		return userFacade.listCurrentUserRequests(pageable);
	}

	@PostMapping(path = "/requests/{requestId}")
	@Operation(summary = "Accept request", description = "Accept a participant request", operationId = "acceptParticipantRequest", tags = {
			"user"})
	void acceptParticipantRequest(@PathVariable UUID requestId) {
		userFacade.acceptRequest(requestId);
	}

	@DeleteMapping(path = "/requests/{requestId}")
	@Operation(summary = "Reject request", description = "Reject a participant request", operationId = "rejectParticipantRequest", tags = {
			"user", "relations"})
	void rejectParticipantRequest(@PathVariable UUID requestId) {
		userFacade.rejectRequest(requestId);
	}

	@GetMapping(path = "/feed")
	@Operation(summary = "Fetch user feed", description = "Fetch the posts in the current user's feed", operationId = "getUserFeed", tags = {
			"user"})
	Page<PostDto> feed(Pageable pageable) {
		return userFacade.listCurrentUserFeed(pageable);
	}
}
