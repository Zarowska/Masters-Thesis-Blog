package blog.cirkle.app.service;

import blog.cirkle.app.model.entity.Participant;
import blog.cirkle.app.model.entity.ParticipantRequest;
import blog.cirkle.app.model.entity.User;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RelationService {
	void followUser(User currentUser, Participant participant);

	void unfollowUser(User currentUser, Participant participant);

	void createRequest(User currentUser, Participant participant,
			ParticipantRequest.ParticipantRequestType participantRequestType);

	void unfriend(User currentUser, User receiver);

	Page<ParticipantRequest> findReceivedRequestsByParticipantId(UUID participantId, Pageable pageable);

	void acceptRequest(Participant receiver, UUID requestId);

	void rejectRequest(Participant receiver, UUID requestId);
}
