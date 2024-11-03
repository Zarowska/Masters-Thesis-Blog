package blog.cirkle.app.facade;

import blog.cirkle.app.api.rest.model.*;
import blog.cirkle.app.api.rest.model.request.CreateUserDto;
import blog.cirkle.app.api.rest.model.request.UpdateUserProfileDto;
import blog.cirkle.app.model.entity.User;
import blog.cirkle.app.utils.SecurityUtils;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserFacade {

	NewUserDto registerUser(CreateUserDto request);

	AuthenticateResponse resetPassword(String key, ResetPasswordDto request);

	Page<ParticipantDto> findAll(String query, Pageable pageable);

	ParticipantDto findByUserId(UUID userId);

	UserProfileDto getProfileByUserId(UUID userId);

	Page<PostDto> findPostsByUserId(UUID userId, Pageable pageable);

	void followUserByUserId(UUID userId);

	void unFollowUserByUserId(UUID userId);

	void friendUserByUserId(UUID userId);

	void unFriendUserByUserId(UUID userId);

	ParticipantDto getCurrentUserInfo();

	Page<PostDto> listCurrentUserPosts(Pageable pageable);

	Page<RequestDto> listCurrentUserRequests(Pageable pageable);

	Page<PostDto> listCurrentUserFeed(Pageable pageable);

	void acceptRequest(UUID requestId);

	void rejectRequest(UUID requestId);

	Page<ParticipantDto> listCurrentUserFollowers(Pageable pageable);

	Page<ParticipantDto> listCurrentUserFriends(Pageable pageable);

	UserProfileDto updateProfile(UpdateUserProfileDto profileUpdate);

	default Page<ParticipantDto> listUserFriends(UUID id, PageRequest pageRequest) {
		User currentUser = SecurityUtils.getCurrentUser();
		if (currentUser.getId().equals(id)) {
			return listCurrentUserFriends(pageRequest);
		} else {
			return Page.empty();
		}
	}

	default Page<ParticipantDto> listUserFollowers(UUID id, PageRequest pageRequest) {
		User currentUser = SecurityUtils.getCurrentUser();
		if (currentUser.getId().equals(id)) {
			return listCurrentUserFollowers(pageRequest);
		} else {
			return Page.empty();
		}
	}

	default Page<RequestDto> listUserRequests(UUID id, PageRequest pageRequest) {
		User currentUser = SecurityUtils.getCurrentUser();
		if (currentUser.getId().equals(id)) {
			return listCurrentUserRequests(pageRequest);
		} else {
			return Page.empty();
		}
	}
}
