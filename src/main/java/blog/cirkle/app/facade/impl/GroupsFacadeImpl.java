package blog.cirkle.app.facade.impl;

import blog.cirkle.app.api.rest.model.*;
import blog.cirkle.app.api.rest.model.request.CreateGroupDto;
import blog.cirkle.app.api.rest.model.request.CreatePostDto;
import blog.cirkle.app.exception.NotImplementedException;
import blog.cirkle.app.facade.GroupsFacade;
import blog.cirkle.app.service.GroupService;
import blog.cirkle.app.service.ModelMapperService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@RequiredArgsConstructor
public class GroupsFacadeImpl implements GroupsFacade {

	private final GroupService groupService;
	private final ModelMapperService modelMapper;

	@Override
	public Page<ParticipantDto> listGroups(@RequestParam(required = false) String query, Pageable pageable) {
		return groupService.findAll(query, pageable).map(modelMapper::toParticipantDto);
	}

	@Override
	public ParticipantDto createGroup(CreateGroupDto request) {
		throw new NotImplementedException();
	}

	@Override
	public GroupProfileDto findProfileById(UUID groupId) {
		throw new NotImplementedException();
	}

	@Override
	public Page<ParticipantDto> findMembersById(UUID groupId) {
		throw new NotImplementedException();
	}

	@Override
	public ParticipantDto findById(UUID groupId) {
		throw new NotImplementedException();
	}

	@Override
	public void joinGroup(UUID groupId) {

	}

	@Override
	public Page<RequestDto> listRequests(UUID groupId) {
		throw new NotImplementedException();
	}

	@Override
	public void acceptRequest(UUID groupId, UUID requestId) {

	}

	@Override
	public void rejectRequest(UUID groupId, UUID requestId) {

	}

	@Override
	public Page<PostDto> listGroupPosts(UUID groupId, Pageable pageable) {
		return null;
	}

	@Override
	public PostDto createPost(UUID groupId, CreatePostDto request) {
		return null;
	}
}
