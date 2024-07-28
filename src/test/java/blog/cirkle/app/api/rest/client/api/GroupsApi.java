package blog.cirkle.app.api.rest.client.api;

import blog.cirkle.app.api.rest.client.model.Pageable;
import blog.cirkle.app.api.rest.client.model.PaginatedResponse;
import blog.cirkle.app.api.rest.model.GroupProfileDto;
import blog.cirkle.app.api.rest.model.ParticipantDto;
import blog.cirkle.app.api.rest.model.RequestDto;
import blog.cirkle.app.api.rest.model.request.CreateGroupDto;
import retrofit2.Call;
import retrofit2.http.*;

public interface GroupsApi {
	@GET("/api/v1/groups")
	Call<PaginatedResponse<ParticipantDto>> listGroups(@Query("pageable") Pageable pageable);

	@POST("/api/v1/groups")
	Call<ParticipantDto> createGroup(@Body CreateGroupDto createGroupDto);

	@POST("/api/v1/groups/{groupId}/requests")
	Call<PaginatedResponse<RequestDto>> listGroupJoinRequest(@Path("groupId") String groupId,
			@Query("pageable") Pageable pageable);

	@POST("/api/v1/groups/{groupId}/requests/{requestId}")
	Call<Void> acceptParticipantRequest(@Path("groupId") String groupId, @Path("requestId") String requestId);

	@DELETE("/api/v1/groups/{groupId}/requests/{requestId}")
	Call<Void> rejectParticipantRequest(@Path("groupId") String groupId, @Path("requestId") String requestId);

	@POST("/api/v1/groups/{groupId}/leave")
	Call<Void> leaveGroupById(@Path("groupId") String groupId);

	@POST("/api/v1/groups/{groupId}/join")
	Call<Void> joinGroupById(@Path("groupId") String groupId);

	@GET("/api/v1/groups/{groupId}")
	Call<ParticipantDto> getGroupById(@Path("groupId") String groupId);

	@GET("/api/v1/groups/{groupId}/profile")
	Call<GroupProfileDto> getGroupProfileById(@Path("groupId") String groupId);

	@GET("/api/v1/groups/{groupId}/members")
	Call<PaginatedResponse<ParticipantDto>> listMembersById(@Path("groupId") String groupId);
}
