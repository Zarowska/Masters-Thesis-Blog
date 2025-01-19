package blog.cirkle.app.facade.impl;

import blog.cirkle.app.api.graphql.model.PageInfo;
import blog.cirkle.app.api.graphql.model.auth.AuthenticateResponse;
import blog.cirkle.app.api.graphql.model.auth.CreateUserInput;
import blog.cirkle.app.api.graphql.model.auth.NewUser;
import blog.cirkle.app.api.graphql.model.auth.ResetPasswordInput;
import blog.cirkle.app.api.graphql.model.comment.Comment;
import blog.cirkle.app.api.graphql.model.comment.CommentPage;
import blog.cirkle.app.api.graphql.model.comment.CreateCommentInput;
import blog.cirkle.app.api.graphql.model.comment.UpdateCommentInput;
import blog.cirkle.app.api.graphql.model.image.Image;
import blog.cirkle.app.api.graphql.model.post.CreatePostInput;
import blog.cirkle.app.api.graphql.model.post.Post;
import blog.cirkle.app.api.graphql.model.post.PostPage;
import blog.cirkle.app.api.graphql.model.post.UpdatePostInput;
import blog.cirkle.app.api.graphql.model.reaction.CreateReactionInput;
import blog.cirkle.app.api.graphql.model.reaction.ReactionList;
import blog.cirkle.app.api.graphql.model.relation.Request;
import blog.cirkle.app.api.graphql.model.relation.RequestPage;
import blog.cirkle.app.api.graphql.model.relation.RequestType;
import blog.cirkle.app.api.graphql.model.user.UpdateUserProfileInput;
import blog.cirkle.app.api.graphql.model.user.User;
import blog.cirkle.app.api.graphql.model.user.UserPage;
import blog.cirkle.app.api.graphql.model.user.UserProfile;
import blog.cirkle.app.api.rest.model.*;
import blog.cirkle.app.api.rest.model.request.*;
import blog.cirkle.app.exception.NotFoundException;
import blog.cirkle.app.facade.AuthFacade;
import blog.cirkle.app.facade.GraphQlFacade;
import blog.cirkle.app.facade.PostsFacade;
import blog.cirkle.app.facade.UserFacade;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GraphQlFacadeImpl implements GraphQlFacade {

	private final UserFacade userFacade;
	private final PostsFacade postsFacade;
	private final AuthFacade authFacade;

	@Override
	public User getCurrentUserInfo() {
		ParticipantDto participantDto = userFacade.getCurrentUserInfo();
		return toUser(participantDto);
	}

	@Override
	public User getUserById(UUID userId) {
		try {
			ParticipantDto participantDto = userFacade.findByUserId(userId);
			return toUser(participantDto);
		} catch (NotFoundException e) {
			return null;
		}
	}

	@Override
	public UserProfile getProfileByUserId(UUID id) {
		UserProfileDto profile = userFacade.getProfileByUserId(id);
		return toDto(profile);
	}

	@Override
	public UserPage listUsers(String query, PageRequest pageRequest) {
		Page<ParticipantDto> all = userFacade.findAll(query, pageRequest);
		return UserPage.builder().pageInfo(toDto(all)).content(all.getContent().stream().map(this::toUser).toList())
				.build();
	}

	@Override
	public UserPage friendsByUserId(UUID id, PageRequest pageRequest) {
		Page<ParticipantDto> page = userFacade.listUserFriends(id, pageRequest);
		return UserPage.builder().pageInfo(toDto(page)).content(page.getContent().stream().map(this::toUser).toList())
				.build();
	}

	@Override
	public UserPage followersByUserId(UUID id, PageRequest pageRequest) {
		Page<ParticipantDto> page = userFacade.listUserFollowers(id, pageRequest);
		return UserPage.builder().pageInfo(toDto(page)).content(page.getContent().stream().map(this::toUser).toList())
				.build();
	}

	@Override
	public RequestPage requestsByUserId(UUID id, PageRequest pageRequest) {
		Page<RequestDto> page = userFacade.listUserRequests(id, pageRequest);
		return RequestPage.builder().pageInfo(toDto(page)).content(page.getContent().stream().map(this::toDto).toList())
				.build();
	}

	@Override
	public Post postByUserIdAndPostId(UUID id, UUID postId) {
		try {
			return toDto(postsFacade.getPostByUserIdAndPostId(id, postId));
		} catch (NotFoundException e) {
			return null;
		}
	}

	@Override
	public PostPage postsByUserId(UUID id, PageRequest pageRequest) {
		Page<PostDto> page = userFacade.findPostsByUserId(id, pageRequest);
		return PostPage.builder().pageInfo(toDto(page)).content(page.getContent().stream().map(this::toDto).toList())
				.build();
	}

	@Override
	public CommentPage commentsByPostId(UUID id, PageRequest pageRequest) {
		Page<CommentDto> page = postsFacade.listRootCommentsByPostId(id, pageRequest);
		return CommentPage.builder().pageInfo(toDto(page)).content(toCommentDto(page.getContent())).build();
	}

	@Override
	public CommentPage commentsByCommentIdAndPostId(UUID postId, Long commentId, PageRequest pageRequest) {
		Page<CommentDto> page = postsFacade.listCommentsByPostIdAndCommentId(postId, commentId, pageRequest);
		return CommentPage.builder().pageInfo(toDto(page)).content(toCommentDto(page.getContent())).build();
	}

	@Override
	public UserProfile updateProfile(UpdateUserProfileInput input) {
		UserProfileDto updatedProfile = userFacade.updateProfile(UpdateUserProfileDto.builder().name(input.getName())
				.handle(input.getHandle()).profileImageId(input.getProfileImageId())
				.coverPhotoImageId(input.getCoverPhotoImageId()).phoneNumber(input.getPhoneNumber()).bio(input.getBio())
				.country(input.getCountry()).city(input.getCity()).build());
		return toDto(updatedProfile);
	}

	@Override
	public Boolean acceptUserRequest(UUID requestId) {
		try {
			userFacade.acceptRequest(requestId);
			return true;
		} catch (NotFoundException e) {
			return false;
		}
	}

	@Override
	public Boolean rejectUserRequest(UUID requestId) {
		try {
			userFacade.rejectRequest(requestId);
			return true;
		} catch (NotFoundException e) {
			return false;
		}
	}

	@Override
	public NewUser registerUser(CreateUserInput input) {
		NewUserDto newUser = userFacade
				.registerUser(CreateUserDto.builder().email(input.getEmail()).fullName(input.getFullName()).build());
		return NewUser.builder().id(UUID.randomUUID()).username(newUser.getUsername()).fullName(newUser.getFullName())
				.avatarUrl(newUser.getAvatarUrl()).passwordResetId(newUser.getPasswordResetId()).build();
	}

	@Override
	public AuthenticateResponse authenticate(String username, String password) {
		authFacade.authenticateByUsernameAndPassword(username, password);
		String token = authFacade.issueTokenForCurrentUser();
		return AuthenticateResponse.builder().token(token).build();
	}

	@Override
	public AuthenticateResponse resetPassword(ResetPasswordInput input) {
		blog.cirkle.app.api.rest.model.AuthenticateResponse authenticateResponse = userFacade
				.resetPassword(input.getPasswordResetToken(), new ResetPasswordDto(input.getPassword()));
		return AuthenticateResponse.builder().token(authenticateResponse.getToken()).build();
	}

	private List<Comment> toCommentDto(List<CommentDto> content) {
		return content.stream().map(this::toCommentDto).toList();
	}

	private Comment toCommentDto(CommentDto dto) {
		return Comment.builder().id(dto.getId()).postId(dto.getPostId()).parentCommentId(dto.getParentCommentId())
				.author(toUser(dto.getAuthor())).text(dto.getText())
				.images(dto.getImages().stream().map(this::toDto).toList()).reactions(toDto(dto.getReactions()))
				.build();
	}

	private Post toDto(PostDto dto) {
		return Post.builder().id(dto.getId()).text(dto.getText())
				.images(dto.getImages().stream().map(this::toDto).toList()).author(toUser(dto.getAuthor()))
				.reactions(toDto(dto.getReactions())).build();
	}

	private Request toDto(RequestDto requestDto) {
		return Request.builder().id(requestDto.getId()).type(RequestType.valueOf(requestDto.getType().name()))
				.sender(toUser(requestDto.getSender())).build();
	}

	private PageInfo toDto(Page all) {
		return PageInfo.builder().totalElements((int) all.getTotalElements()).totalPages(all.getTotalPages())
				.pageNumber(all.getNumber()).pageSize(all.getSize()).first(all.isFirst()).last(all.isLast()).build();
	}

	private UserProfile toDto(UserProfileDto profile) {
		return UserProfile.builder().bio(profile.getBio()).city(profile.getCity()).country(profile.getCountry())
				.name(profile.getName()).handle(profile.getHandle()).phoneNumber(profile.getPhoneNumber())
				.coverPhoto(toDto(profile.getCoverPhoto())).profileImage(toDto(profile.getProfileImage())).build();
	}

	private Image toDto(ImageDto image) {
		return Image.builder().id(image.getId()).uri(image.getUri()).reactions(toDto(image.getReactions())).build();
	}

	private List<ReactionList> toDto(ReactionsDto reactions) {
		if (reactions == null) {
			return null;
		}
		return toReactionListDto(reactions.getReactions());
	}

	private List<ReactionList> toReactionListDto(@Valid List<blog.cirkle.app.api.rest.model.ReactionList> reactions) {
		return reactions.stream()
				.map(it -> ReactionList.builder().reactionCount(it.getReactionCount())
						.reactionValue(it.getReactionValue()).participants(toDto(it.getParticipants())).build())
				.toList();
	}

	private List<User> toDto(List<ParticipantDto> participants) {
		if (participants == null) {
			return null;
		}
		return participants.stream().map(this::toUser).toList();
	}

	private User toUser(ParticipantDto participantDto) {
		return User.builder().id(participantDto.getId()).name(participantDto.getName())
				.avatarUrl(participantDto.getAvatarUrl()).isGroup(participantDto.getIsGroup()).build();
	}

	@Override
	public Post createPost(CreatePostInput input) {
		return toDto(postsFacade
				.createPost(CreatePostDto.builder().text(input.getText()).images(input.getImages()).build()));
	}

	@Override
	public Post updatePost(UUID postId, UpdatePostInput input) {
		return toDto(postsFacade.updatePost(postId,
				UpdateMediaDto.builder().text(input.getText()).images(input.getImages()).build()));
	}

	@Override
	public Boolean deletePost(UUID postId) {
		try {
			postsFacade.deletePostById(postId);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<ReactionList> addReactionToPost(UUID postId, CreateReactionInput input) {
		return toDto(postsFacade.createReactionByPostId(postId, CreateReactionDto.of(input.getValue())));
	}

	@Override
	public Comment addCommentToPost(UUID postId, CreateCommentInput input) {
		return toCommentDto(postsFacade.createComment(postId, null,
				CreateCommentDto.builder().text(input.getText()).images(input.getImages()).build()));
	}

	@Override
	public Comment updateComment(UUID postId, Long commentId, UpdateCommentInput input) {
		return toCommentDto(postsFacade.updateCommentByPostIdAndCommentId(postId, commentId,
				UpdateCommentDto.builder().text(input.getText()).images(input.getImages()).build()));
	}

	@Override
	public Boolean deleteComment(UUID postId, Long commentId) {
		try {
			postsFacade.deleteCommentByPostIdAndCommentId(postId, commentId);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<ReactionList> reactOnComment(UUID postId, Long commentId, CreateReactionInput input) {
		return toReactionListDto(postsFacade
				.createReactionByPostIdAndCommentId(postId, commentId, CreateReactionDto.of(input.getValue()))
				.getReactions());
	}

	@Override
	public Comment addCommentToComment(UUID postId, Long commentId, CreateCommentInput input) {
		return toCommentDto(postsFacade.createComment(postId, commentId,
				CreateCommentDto.builder().text(input.getText()).images(input.getImages()).build()));
	}

	@Override
	public Boolean followUser(UUID userId) {
		try {
			userFacade.followUserByUserId(userId);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Boolean unFollowUser(UUID userId) {
		try {
			userFacade.unFollowUserByUserId(userId);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Boolean friendUser(UUID userId) {
		try {
			userFacade.friendUserByUserId(userId);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Boolean unFriendUser(UUID userId) {
		try {
			userFacade.unFriendUserByUserId(userId);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
