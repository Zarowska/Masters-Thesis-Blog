package blog.cirkle.app.facade;

import blog.cirkle.app.api.rest.model.GroupProfileDto;
import blog.cirkle.app.api.rest.model.ParticipantDto;
import blog.cirkle.app.api.rest.model.PostDto;
import blog.cirkle.app.api.rest.model.RequestDto;
import blog.cirkle.app.api.rest.model.request.CreateGroupDto;
import blog.cirkle.app.api.rest.model.request.CreatePostDto;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface GroupsFacade {

	Page<ParticipantDto> listGroups(String query, Pageable pageable);

	ParticipantDto createGroup(CreateGroupDto request);

	GroupProfileDto findProfileById(UUID groupId);

	Page<ParticipantDto> findMembersById(UUID groupId);

	ParticipantDto findById(UUID groupId);

	void joinGroup(UUID groupId);

	Page<RequestDto> listRequests(UUID groupId);

	void acceptRequest(UUID groupId, UUID requestId);

	void rejectRequest(UUID groupId, UUID requestId);

	Page<PostDto> listGroupPosts(UUID groupId, Pageable pageable);

	PostDto createPost(UUID groupId, CreatePostDto request);
}
