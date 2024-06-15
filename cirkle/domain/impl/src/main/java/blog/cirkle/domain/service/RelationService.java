package blog.cirkle.domain.service;

import blog.cirkle.domain.entity.participant.Participant;
import blog.cirkle.domain.entity.participant.Relation;
import blog.cirkle.domain.entity.participant.RelationRequest;
import blog.cirkle.domain.model.newModel.RelationType;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RelationService {

	Page<Relation> findRelationsByType(UUID participantId, Set<RelationType> filter, Pageable pageable);

	void createRelationRequest(Participant initiator, Participant target, RelationType relationType);

	Page<RelationRequest> listRelationRequests(UUID id, Set<RelationType> friend, Pageable pageable);

	void removeRelation(Participant initiator, Participant target, RelationType relationType);

	void acceptRequest(UUID targetId, UUID requestId);

	void rejectRequest(UUID targetId, UUID requestId);
}
