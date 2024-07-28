package blog.cirkle.app.service.impl;

import static blog.cirkle.app.model.entity.SystemConfigEntry.DEFAULT_IMAGE_COVER;
import static blog.cirkle.app.model.entity.SystemConfigEntry.DEFAULT_IMAGE_PROFILE;

import blog.cirkle.app.api.rest.model.*;
import blog.cirkle.app.model.entity.*;
import blog.cirkle.app.service.ImageService;
import blog.cirkle.app.service.ModelMapperService;
import blog.cirkle.app.service.SystemConfigService;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ModelMapperServiceImpl implements ModelMapperService {

	private final SystemConfigService systemConfigService;
	private final ImageService imageService;

	@Override
	public NewUserDto newUserDto(User user, PasswordChangeRequest passwordRequest) {
		return NewUserDto.builder().id(user.getId()).username(user.getProfile().getHandle())
				.fullName(user.getProfile().getName())
				.passwordResetId(
						"%s-%s".formatted(passwordRequest.getId(), passwordRequest.getRequestId()).replace("-", ""))
				.avatarUrl(imageUrl(user.getProfile().getProfileImage())).build();
	}

	private String imageUrl(Image image) {
		Properties systemProperties = systemConfigService.getSystemProperties();
		return "/public/api/images/" + orDefaultImage(systemProperties, image, DEFAULT_IMAGE_PROFILE);
	}

	@Override
	public ParticipantDto toParticipantDto(Participant participant) {
		if (participant instanceof User user) {
			UserProfile profile = user.getProfile();
			return ParticipantDto.builder().id(user.getId()).name(profile.getName())
					.avatarUrl(imageUrl(profile.getProfileImage())).isGroup(false).build();
		} else {
			Group group = (Group) participant;
			GroupProfile profile = group.getProfile();
			return ParticipantDto.builder().id(group.getId()).name(profile.getName())
					.avatarUrl(imageUrl(profile.getProfileImage())).isGroup(false).build();
		}
	}

	@Override
	public ImageDto toImageDto(Image image) {
		return ImageDto.builder().id(image.getId()).uri(imageUrl(image)).reactions(toReactionDto(image.getReactions()))
				.build();
	}

	@Override
	public List<ImageDto> toImageDto(Collection<Image> images) {
		return images.stream().map(this::toImageDto).toList();
	}

	@Override
	public PostDto toPostDto(Post post) {
		return PostDto.builder().id(post.getId()).author(toParticipantDto(post.getAuthor())).text(post.getTextContent())
				.images(toImageDto(post.getImages())).reactions(toReactionDto(post.getReactions())).build();
	}

	@Override
	public CommentDto toCommentDto(Comment comment) {
		return CommentDto.builder().id(comment.getId()).author(toParticipantDto(comment.getAuthor()))
				.postId(comment.getPost().getId())
				.parentCommentId(Optional.ofNullable(comment.getParentComment()).map(Comment::getId).orElse(null))
				.text(comment.getTextContent()).images(toImageDto(comment.getImages()))
				.reactions(toReactionDto(comment.getReactions())).build();
	}

	public ReactionsDto toReactionDto(Collection<Reaction> reactions) {
		Map<Integer, List<Reaction>> reactionMap = reactions.stream()
				.collect(Collectors.groupingBy(Reaction::getValue));
		return ReactionsDto.builder().reactions(reactionMap.entrySet().stream()
				.map(e -> new ReactionList(e.getKey(), e.getValue().size(),
						e.getValue().stream().map(Reaction::getUser).map(this::toParticipantDto).toList()))
				.toList()).build();
	}

	@Override
	public UserProfileDto toUserProfileDto(UserProfile profile) {
		Properties systemProperties = systemConfigService.getSystemProperties();
		Address address = profile.getAddress();
		if (address == null) {
			address = new Address();
		}

		Image profileImage = Optional.ofNullable(profile.getProfileImage()).orElseGet(
				() -> imageService.findById(UUID.fromString(systemProperties.getProperty(DEFAULT_IMAGE_PROFILE))));

		Image coverPhoto = Optional.ofNullable(profile.getCoverPhoto()).orElseGet(
				() -> imageService.findById(UUID.fromString(systemProperties.getProperty(DEFAULT_IMAGE_COVER))));

		return UserProfileDto.builder().name(profile.getName()).bio(profile.getBio()).city(address.getCity())
				.country(address.getCountry()).coverPhoto(toImageDto(coverPhoto)).profileImage(toImageDto(profileImage))
				.handle(profile.getHandle()).phoneNumber(profile.getPhoneNumber()).build();
	}

	@Override
	public RequestDto toRequestDto(ParticipantRequest request) {
		return RequestDto.builder().id(request.getId()).sender(toParticipantDto(request.getSender()))
				.type(request.getType()).build();
	}

	private Object orDefaultImage(Properties systemProperties, Image image, String key) {
		if (image == null) {
			return systemProperties.getProperty(key);
		}
		return image.getId();
	}
}
