package blog.cirkle.app.api.rest;

import blog.cirkle.app.api.rest.model.GroupProfileDto;
import blog.cirkle.app.api.rest.model.ParticipantDto;
import blog.cirkle.app.api.rest.model.PostDto;
import blog.cirkle.app.api.rest.model.RequestDto;
import blog.cirkle.app.api.rest.model.request.CreateGroupDto;
import blog.cirkle.app.api.rest.model.request.CreatePostDto;
import blog.cirkle.app.facade.GroupsFacade;
import io.swagger.v3.oas.annotations.Operation;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/v1/groups")
@RequiredArgsConstructor
public class GroupsController {

	private final GroupsFacade groupsFacade;

	@GetMapping
	@Operation(summary = "List Groups", description = "List all groups with pagination", operationId = "listGroups", tags = {
			"groups"})
	Page<ParticipantDto> listGroups(@RequestParam(required = false) String query, @PageableDefault Pageable pageable) {
		return groupsFacade.listGroups(query, pageable);
	}

	@PostMapping
	@Operation(summary = "Create Group", description = "Creates a new group", operationId = "createGroup", tags = {
			"groups"})
	ParticipantDto createGroup(@RequestBody CreateGroupDto request) {
		return groupsFacade.createGroup(request);
	}

	@PostMapping(path = "/{groupId}/posts")
	@Operation(summary = "Create a new post in group", description = "Creates new post ing group space", operationId = "createPostInGroup", tags = {
			"groups"})
	PostDto createNewPost(@PathVariable UUID groupId, @RequestBody CreatePostDto request) {
		return groupsFacade.createPost(groupId, request);
	}

	@GetMapping(path = "/{groupId}/posts")
	@Operation(summary = "Lists post in group", description = "Lists post in group", operationId = "listPostInGroup", tags = {
			"groups"})
	Page<PostDto> listGroupPosts(@PathVariable UUID groupId, @PageableDefault Pageable pageable) {
		return groupsFacade.listGroupPosts(groupId, pageable);
	}

	@GetMapping(path = "/{groupId}")
	@Operation(summary = "Get group by id", description = "Fetch a specific group", operationId = "getGroupById", tags = {
			"groups"})
	ParticipantDto getGroupById(@PathVariable UUID groupId) {
		return groupsFacade.findById(groupId);
	}

	@GetMapping(path = "/{groupId}/profile")
	@Operation(summary = "Get profile by group id", description = "Fetch a specific group profile", operationId = "getGroupProfileById", tags = {
			"groups"})
	GroupProfileDto getGroupProfileById(@PathVariable UUID groupId) {
		return groupsFacade.findProfileById(groupId);
	}

	@GetMapping(path = "/{groupId}/members")
	@Operation(summary = "List group members by group id", description = "List all members of a specific group", operationId = "listMembersById", tags = {
			"groups"})
	Page<ParticipantDto> listMembersById(@PathVariable UUID groupId) {
		return groupsFacade.findMembersById(groupId);
	}

	@PostMapping(path = "/{groupId}/join")
	@Operation(summary = "Join group by id", description = "Join a specific group", operationId = "joinGroupById", tags = {
			"groups", "relations"})
	void joinGroupById(@PathVariable UUID groupId) {
		groupsFacade.joinGroup(groupId);
	}

	@PostMapping(path = "/{groupId}/leave")
	@Operation(summary = "Leave group by id", description = "Leave a specific group", operationId = "leaveGroupById", tags = {
			"groups", "relations"})
	void leaveGroupById(@PathVariable UUID groupId) {
		groupsFacade.joinGroup(groupId);
	}

	@PostMapping(path = "/{groupId}/requests")
	@Operation(summary = "List group join requests by group id", description = "List all join requests for a specific group", operationId = "listGroupJoinRequest", tags = {
			"groups", "relations"})
	Page<RequestDto> listGroupJoinRequest(@PathVariable UUID groupId, @PageableDefault Pageable pageable) {
		return groupsFacade.listRequests(groupId);
	}

	@PostMapping(path = "/{groupId}/requests/{requestId}")
	@Operation(summary = "Accept participant request", description = "Accept a specific participant request for a group", operationId = "acceptParticipantRequest", tags = {
			"groups", "relations"})
	void acceptParticipantRequest(@PathVariable UUID groupId, @PathVariable UUID requestId) {
		groupsFacade.acceptRequest(groupId, requestId);
	}

	@DeleteMapping(path = "/{groupId}/requests/{requestId}")
	@Operation(summary = "Reject participant request", description = "Reject a specific participant request for a group", operationId = "rejectParticipantRequest", tags = {
			"groups", "relations"})
	void rejectParticipantRequest(@PathVariable UUID groupId, @PathVariable UUID requestId) {
		groupsFacade.rejectRequest(groupId, requestId);
	}
}
