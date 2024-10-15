package blog.cirkle.app.api.rest.client.endpoints;

import blog.cirkle.app.api.rest.client.ApiClient;
import blog.cirkle.app.api.rest.client.api.GroupsApi;
import blog.cirkle.app.api.rest.client.endpoints.utils.PageableQueryMapConverter;
import blog.cirkle.app.api.rest.client.model.Pageable;
import blog.cirkle.app.api.rest.client.model.PaginatedResponse;
import blog.cirkle.app.api.rest.model.GroupProfileDto;
import blog.cirkle.app.api.rest.model.ParticipantDto;
import blog.cirkle.app.api.rest.model.RequestDto;
import blog.cirkle.app.api.rest.model.request.CreateGroupDto;
import java.util.Map;

public class GroupsEndpoint extends AbstractEndpoint<GroupsApi> {
	public GroupsEndpoint(ApiClient.ClientContext context) {
		super(context, GroupsApi.class);
	}

	public PaginatedResponse<ParticipantDto> listGroups(Pageable pageable) {
		Map<String, String> pageableMap = PageableQueryMapConverter.toMap(pageable);
		return call(api.listGroups(pageableMap)).body();
	}

	public ParticipantDto createGroup(CreateGroupDto createGroupDto) {
		return call(api.createGroup(createGroupDto)).body();
	}

	public PaginatedResponse<RequestDto> listGroupJoinRequest(String groupId, Pageable pageable) {
		Map<String, String> pageableMap = PageableQueryMapConverter.toMap(pageable);
		return call(api.listGroupJoinRequest(groupId, pageableMap)).body();
	}

	public Void acceptParticipantRequest(String groupId, String requestId) {
		return call(api.acceptParticipantRequest(groupId, requestId)).body();
	}

	public Void rejectParticipantRequest(String groupId, String requestId) {
		return call(api.rejectParticipantRequest(groupId, requestId)).body();
	}

	public Void leaveGroupById(String groupId) {
		return call(api.leaveGroupById(groupId)).body();
	}

	public Void joinGroupById(String groupId) {
		return call(api.joinGroupById(groupId)).body();
	}

	public ParticipantDto getGroupById(String groupId) {
		return call(api.getGroupById(groupId)).body();
	}

	public GroupProfileDto getGroupProfileById(String groupId) {
		return call(api.getGroupProfileById(groupId)).body();
	}

	public PaginatedResponse<ParticipantDto> listMembersById(String groupId) {
		return call(api.listMembersById(groupId)).body();
	}
}
