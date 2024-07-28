package blog.cirkle.app.service.impl;

import static blog.cirkle.app.model.entity.ParticipantRequest.ParticipantRequestType.FRIEND;

import blog.cirkle.app.exception.BadRequestException;
import blog.cirkle.app.exception.NotFoundException;
import blog.cirkle.app.model.entity.Group;
import blog.cirkle.app.model.entity.Participant;
import blog.cirkle.app.model.entity.ParticipantRequest;
import blog.cirkle.app.model.entity.User;
import blog.cirkle.app.repository.ParticipantRequestRepository;
import blog.cirkle.app.service.RelationService;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RelationServiceImpl implements RelationService {

	private final ParticipantRequestRepository requestRepository;

	@Override
	public void followUser(User currentUser, Participant participant) {
		participant.getFollowers().add(currentUser);
	}

	@Override
	public void unfollowUser(User currentUser, Participant participant) {
		participant.getFollowers().remove(currentUser);
	}

	@Override
	public void createRequest(User sender, Participant receiver, ParticipantRequest.ParticipantRequestType type) {
		Optional<ParticipantRequest> existingRequest = requestRepository
				.findBySender_IdAndReceiver_IdAndType(sender.getId(), receiver.getId(), type);
		if (existingRequest.isEmpty()) {
			if (receiver instanceof Group group) {
				processGroupReceiver(sender, group, type);
			} else if (receiver instanceof User user) {
				processUserReceiver(sender, user, type);
			}
		}
	}

	@Override
	public void unfriend(User sender, User receiver) {
		sender.getFriends().remove(receiver);
		sender.getFollowers().remove(receiver);
		receiver.getFriends().remove(sender);
		receiver.getFollowers().remove(sender);
	}

	@Override
	public Page<ParticipantRequest> findReceivedRequestsByParticipantId(UUID participantId, Pageable pageable) {
		return requestRepository.findByReceiver_Id(participantId, pageable);
	}

	@Override
	public void acceptRequest(Participant receiver, UUID requestId) {
		ParticipantRequest request = requestRepository.findById(requestId)
				.filter(it -> it.getReceiver().getId().equals(receiver.getId()))
				.orElseThrow(() -> NotFoundException.resource("request", Map.of("id", requestId)));

		if (receiver instanceof Group group) {
			acceptByGroup(request.getSender(), group, request);
		} else if (receiver instanceof User user) {
			acceptByUser(request.getSender(), user, request);
		}
		requestRepository.delete(request);
	}

	private void acceptByUser(Participant sender, User receiver, ParticipantRequest request) {
		if (sender instanceof User user) {
			user.getFriends().add(receiver);
			user.getFollowers().add(receiver);
			receiver.getFriends().add(user);
			receiver.getFollowers().add(user);
		}
	}

	private void acceptByGroup(Participant sender, Group receiver, ParticipantRequest request) {
		if (sender instanceof User user) {
			receiver.getMembers().add(user);
		}
	}

	@Override
	public void rejectRequest(Participant receiver, UUID requestId) {
		requestRepository.deleteByReceiver_IdAndId(receiver.getId(), requestId);
	}

	private void processUserReceiver(User sender, User receiver, ParticipantRequest.ParticipantRequestType type) {
		if (type == FRIEND) {
			if (!receiver.getFriends().contains(sender)) {
				applyRequest(sender, receiver, type);
			}
		} else {
			throw new BadRequestException("Invalid request type: " + type);
		}
	}

	private void processGroupReceiver(User sender, Group receiver, ParticipantRequest.ParticipantRequestType type) {
		if (type == FRIEND) {
			if (!receiver.getMembers().contains(sender)) {
				applyRequest(sender, receiver, type);
			}
		} else {
			throw new BadRequestException("Invalid request type: " + type);
		}
	}

	private void applyRequest(User sender, Participant receiver, ParticipantRequest.ParticipantRequestType type) {
		ParticipantRequest request = requestRepository
				.save(ParticipantRequest.builder().sender(sender).receiver(receiver).type(type).build());
		sender.getSentRequests().add(request);
		receiver.getReceivedRequests().add(request);
	}
}
