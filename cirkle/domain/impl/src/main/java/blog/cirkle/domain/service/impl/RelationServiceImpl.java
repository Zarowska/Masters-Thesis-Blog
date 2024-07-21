package blog.cirkle.domain.service.impl;

import blog.cirkle.domain.entity.participant.Participant;
import blog.cirkle.domain.entity.participant.Relation;
import blog.cirkle.domain.entity.participant.RelationRequest;
import blog.cirkle.domain.exception.BadRequestException;
import blog.cirkle.domain.exception.ResourceNotFoundException;
import blog.cirkle.domain.model.response.RelationType;
import blog.cirkle.domain.repository.participant.RelationRepository;
import blog.cirkle.domain.repository.participant.RelationRequestRepository;
import blog.cirkle.domain.service.RelationService;
import java.util.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RelationServiceImpl implements RelationService {

	private final RelationRepository relationRepository;
	private final RelationRequestRepository relationRequestRepository;

	@Override
	public Page<Relation> findRelationsByType(UUID participantId, Set<RelationType> filter, Pageable pageable) {
		return relationRepository.findByOwner_IdAndTypeIn(participantId, filter, pageable);
	}

	@Override
	public void createRelationRequest(@NonNull Participant initiator, @NonNull Participant target,
			RelationType relationType) {
		validateRelation(initiator, target, relationType);
		handleRelationType(initiator, target, relationType);
	}

	private void handleRelationType(Participant initiator, Participant target, RelationType relationType) {
		switch (relationType) {
			case FRIEND :
				createFriendRequest(initiator, target, relationType);
				break;
			case FOLLOWED :
				createFollowRelation(initiator, target);
				break;
			default :
				throw new UnsupportedOperationException("Relation " + relationType + " not supported yet");
		}
	}

	private void createFriendRequest(Participant initiator, Participant target, RelationType relationType) {
		RelationRequest request = new RelationRequest().setInitiator(initiator).setTarget(target).setType(relationType);
		relationRequestRepository.save(request);
	}

	private void createFollowRelation(Participant initiator, Participant target) {
		List<Relation> relations = List.of(new Relation(initiator, target, RelationType.FOLLOWED),
				new Relation(target, initiator, RelationType.FOLLOWER));
		relationRepository.saveAll(relations);
	}

	@Override
	public void removeRelation(Participant initiator, Participant target, RelationType relationType) {
		verifyRelationExists(initiator, target, relationType);

		Set<Participant> party = Set.of(initiator, target);
		relationRepository.deleteByTypeAndOwnerInAndRelatedIn(relationType, party, party);
	}

	private void verifyRelationExists(Participant initiator, Participant target, RelationType relationType) {
		relationRepository.findByOwnerAndRelatedAndType(initiator, target, relationType)
				.orElseThrow(() -> resourceNotFoundException(relationType, initiator.getId(), target.getId()));
	}

	private ResourceNotFoundException resourceNotFoundException(RelationType relationType, UUID initiatorId,
			UUID targetId) {
		return new ResourceNotFoundException(
				"Relation of type %s between %s and %s not exists".formatted(relationType, initiatorId, targetId));
	}

	@Override
	public void acceptRequest(UUID targetId, UUID requestId) {
		RelationRequest request = validateAndFindRequest(targetId, requestId);
		completeFriendRelationRequest(request);
	}

	private RelationRequest validateAndFindRequest(UUID targetId, UUID requestId) {
		RelationRequest request = relationRequestRepository.findById(requestId)
				.orElseThrow(() -> new ResourceNotFoundException("Relation request", Map.of("id", requestId)));
		if (!request.getTarget().getId().equals(targetId)) {
			throw new BadRequestException("You can accept only requests made to you");
		}
		return request;
	}

	private void completeFriendRelationRequest(RelationRequest request) {
		if (request.getType() == RelationType.FRIEND) {
			List<Relation> relations = Arrays.asList(
					new Relation(request.getInitiator(), request.getTarget(), RelationType.FRIEND),
					new Relation(request.getTarget(), request.getInitiator(), RelationType.FRIEND));
			relationRepository.saveAll(relations);
		}
	}

	@Override
	public void rejectRequest(UUID targetId, UUID requestId) {
		RelationRequest request = validateAndFindRequest(targetId, requestId);
		relationRequestRepository.deleteById(request.getId());
	}

	private void validateRelation(Participant initiator, Participant target, RelationType relationType) {
		assertNotSelfRelation(initiator, target);
		assertNotExistingRelation(initiator, target, relationType);
		assertUserType(initiator, "Initiator");
		checkOtherConditionsBasedOnRelationType(initiator, target, relationType);
	}

	private void assertNotSelfRelation(Participant initiator, Participant target) {
		if (initiator.equals(target)) {
			throw new BadRequestException("You can't create relation with self!");
		}
	}

	private void assertNotExistingRelation(Participant initiator, Participant target, RelationType relationType) {
		relationRepository.findByOwnerAndRelatedAndType(initiator, target, relationType).ifPresent(it -> {
			throw new BadRequestException("%s relation between %s and %s already exists".formatted(relationType.name(),
					initiator.getId(), target.getId()));
		});
	}

	private void assertUserType(Participant participant, String type) {
		if (participant.getType() != Participant.ParticipantType.USER) {
			throw new BadRequestException("Operation requires %s to be of type USER".formatted(type));
		}
	}

	private void checkOtherConditionsBasedOnRelationType(Participant initiator, Participant target,
			RelationType relationType) {
		if (relationType == RelationType.FRIEND) {
			assertUserType(target, "Target");
			if (relationRequestRepository.existsByInitiator_IdAndTarget_IdAndType(initiator.getId(), target.getId(),
					relationType)) {
				throw new BadRequestException("%s relation request between %s and %s already sent"
						.formatted(relationType.name(), initiator.getId(), target.getId()));
			}
		}
	}

	@Override
	public Page<RelationRequest> listRelationRequests(UUID id, Set<RelationType> filter, Pageable pageable) {
		return relationRequestRepository.findByTarget_IdAndTypeIn(id, filter, pageable);
	}
}