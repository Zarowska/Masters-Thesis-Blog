package blog.cirkle.app.facade.impl;

import static blog.cirkle.app.model.entity.ParticipantRequest.ParticipantRequestType.FRIEND;
import static blog.cirkle.app.utils.SecurityUtils.getCurrentUser;

import blog.cirkle.app.api.rest.model.*;
import blog.cirkle.app.api.rest.model.UserProfileDto;
import blog.cirkle.app.api.rest.model.request.CreateUserDto;
import blog.cirkle.app.api.rest.model.request.UpdateUserProfileDto;
import blog.cirkle.app.exception.BadRequestException;
import blog.cirkle.app.exception.NotFoundException;
import blog.cirkle.app.facade.AuthFacade;
import blog.cirkle.app.facade.UserFacade;
import blog.cirkle.app.model.entity.*;
import blog.cirkle.app.repository.PasswordChangeRequestRepository;
import blog.cirkle.app.service.*;
import blog.cirkle.app.utils.UUIDExtractor;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserFacadeImpl implements UserFacade {

	private final UserService userService;
	private final ModelMapperService modelMapper;
	private final AuthFacade authFacade;
	private final PostService postService;
	private final RelationService relationService;
	private final FeedService feedService;
	private final PasswordChangeRequestRepository passwordChangeRequestRepository;
	private final ImageService imageService;

	@Override
	public NewUserDto registerUser(CreateUserDto request) {
		User user = userService.createUser(request);

		PasswordChangeRequest passwordRequest = passwordChangeRequestRepository.save(new PasswordChangeRequest(user));
		return modelMapper.newUserDto(user, passwordRequest);
	}

	@Override
	@Transactional
	public AuthenticateResponse resetPassword(String key, ResetPasswordDto request) {
		try {
			UUID[] uuids = UUIDExtractor.extractUUIDs(key);
			Optional<PasswordChangeRequest> resetRequest = passwordChangeRequestRepository
					.findByIdAndRequestId(uuids[0], uuids[1]);
			if (resetRequest.isPresent()) {
				PasswordChangeRequest entity = resetRequest.get();
				passwordChangeRequestRepository.delete(entity);
				userService.updatePassword(entity.getUser(), request);
				authFacade.authenticateByUsernameAndPassword(entity.getUser().getEmail(), request.getPassword());
				return new AuthenticateResponse(authFacade.issueTokenForCurrentUser());
			} else {
				throw NotFoundException.resource("Password reset request", Map.of("key", key));
			}
		} catch (Exception e) {
			throw new BadRequestException("Unable to reset password: " + e.getMessage());
		}
	}

	@Override
	public Page<ParticipantDto> findAll(String query, Pageable pageable) {
		return userService.findAll(query, pageable).map(modelMapper::toParticipantDto);
	}

	@Override
	public ParticipantDto findByUserId(UUID userId) {
		return modelMapper.toParticipantDto(userService.findById(userId));
	}

	@Override
	public UserProfileDto getProfileByUserId(UUID userId) {
		return modelMapper.toUserProfileDto(userService.findById(userId).getProfile());
	}

	@Override
	public Page<PostDto> findPostsByUserId(UUID userId, Pageable pageable) {
		return postService.findAllByUserSpace(userId, pageable).map(modelMapper::toPostDto);
	}

	@Override
	public void followUserByUserId(UUID userId) {
		User user = userService.findById(userId);
		relationService.followUser(getCurrentUser(), user);
	}

	@Override
	public void unFollowUserByUserId(UUID userId) {
		User followed = userService.findById(userId);
		relationService.unfollowUser(getCurrentUser(), followed);
	}

	@Override
	public void friendUserByUserId(UUID userId) {
		User receiver = userService.findById(userId);
		relationService.createRequest(userService.findById(getCurrentUser().getId()), receiver, FRIEND);
	}

	@Override
	public void unFriendUserByUserId(UUID userId) {
		User receiver = userService.findById(userId);
		relationService.unfriend(userService.findById(getCurrentUser().getId()), receiver);
	}

	@Override
	public ParticipantDto getCurrentUserInfo() {
		return modelMapper.toParticipantDto(getCurrentUser());
	}

	@Override
	public Page<PostDto> listCurrentUserPosts(Pageable pageable) {
		return postService.findAllByUserSpace(getCurrentUser().getId(), pageable).map(modelMapper::toPostDto);
	}

	@Override
	public Page<RequestDto> listCurrentUserRequests(Pageable pageable) {
		return relationService.findReceivedRequestsByParticipantId(getCurrentUser().getId(), pageable)
				.map(modelMapper::toRequestDto);
	}

	@Override
	public Page<PostDto> listCurrentUserFeed(Pageable pageable) {
		return feedService.getPosts(getCurrentUser().getId(), pageable).map(modelMapper::toPostDto);
	}

	@Override
	public void acceptRequest(UUID requestId) {
		relationService.acceptRequest(userService.findById(getCurrentUser().getId()), requestId);
	}

	@Override
	public void rejectRequest(UUID requestId) {
		relationService.rejectRequest(userService.findById(getCurrentUser().getId()), requestId);
	}

	@Override
	public Page<ParticipantDto> listCurrentUserFollowers(Pageable pageable) {
		return userService.listFollowersByUserId(getCurrentUser().getId(), pageable).map(modelMapper::toParticipantDto);
	}

	@Override
	public Page<ParticipantDto> listCurrentUserFriends(Pageable pageable) {
		return userService.listFriendsByUserId(getCurrentUser().getId(), pageable).map(modelMapper::toParticipantDto);
	}

	@Override
	@Transactional
	public UserProfileDto updateProfile(UpdateUserProfileDto profileUpdate) {
		User currentUser = userService.findById(getCurrentUser().getId());
		UserProfile profile = currentUser.getProfile();
		Optional.ofNullable(profileUpdate.getName()).ifPresent(profile::setName);
		Optional.ofNullable(profileUpdate.getBio()).ifPresent(profile::setBio);
		Optional.ofNullable(profileUpdate.getHandle()).ifPresent(profile::setHandle);
		Optional.ofNullable(profileUpdate.getProfileImageId()).ifPresent(imageId -> {
			Image image = imageService.findById(imageId);
			if (image.getOwner().getId().equals(currentUser.getId())) {
				profile.setProfileImage(image);
			} else {
				throw new BadRequestException(
						"Image with " + imageId + " not belongs to userId=" + currentUser.getId());
			}
		});
		Optional.ofNullable(profileUpdate.getCoverPhotoImageId()).ifPresent(imageId -> {
			Image image = imageService.findById(imageId);
			if (image.getOwner().getId().equals(currentUser.getId())) {
				profile.setCoverPhoto(image);
			} else {
				throw new BadRequestException(
						"Image with " + imageId + " not belongs to userId=" + currentUser.getId());
			}
		});
		Optional.ofNullable(profileUpdate.getPhoneNumber())
				.ifPresent(phoneNumber -> profile.setPhoneNumber(phoneNumber));
		Optional.ofNullable(profileUpdate.getCountry()).ifPresent(country -> {
			if (profile.getAddress() == null) {
				profile.setAddress(new Address());
			}
			profile.getAddress().setCountry(country);
		});
		Optional.ofNullable(profileUpdate.getCity()).ifPresent(city -> {
			if (profile.getAddress() == null) {
				profile.setAddress(new Address());
			}
			profile.getAddress().setCity(city);
		});
		return modelMapper.toUserProfileDto(profile);
	}
}
