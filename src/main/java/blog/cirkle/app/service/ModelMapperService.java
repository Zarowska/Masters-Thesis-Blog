package blog.cirkle.app.service;

import blog.cirkle.app.api.rest.model.*;
import blog.cirkle.app.model.entity.*;
import java.util.Collection;
import java.util.List;

public interface ModelMapperService {
	NewUserDto newUserDto(User user, PasswordChangeRequest passwordRequest);

	ParticipantDto toParticipantDto(Participant participant);

	ImageDto toImageDto(Image image);

	List<ImageDto> toImageDto(Collection<Image> images);

	PostDto toPostDto(Post post);

	CommentDto toCommentDto(Comment comment);

	ReactionsDto toReactionDto(Collection<Reaction> reaction);

	UserProfileDto toUserProfileDto(UserProfile profile);

	RequestDto toRequestDto(ParticipantRequest participantRequest);

}
