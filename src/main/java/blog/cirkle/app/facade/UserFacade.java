package blog.cirkle.app.facade;

import blog.cirkle.app.api.rest.model.*;
import blog.cirkle.app.api.rest.model.UserProfileDto;
import blog.cirkle.app.api.rest.model.request.CreateUserDto;
import java.util.UUID;
import org.springframework.data.domain.Page;
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

	void unfriendUserByUserId(UUID userId);

	ParticipantDto getCurrentUserInfo();

	Page<PostDto> listCurrentUserPosts(Pageable pageable);

	Page<RequestDto> listCurrentUserRequests(Pageable pageable);

	Page<PostDto> listCurrentUserFeed(Pageable pageable);

	void acceptRequest(UUID requestId);

	void rejectRequest(UUID requestId);

	Page<ParticipantDto> listCurrentUserFollowers(Pageable pageable);

	Page<ParticipantDto> listCurrentUserFriends(Pageable pageable);
}
