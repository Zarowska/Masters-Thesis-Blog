package blog.cirkle.domain.facade.mappers;

import static blog.cirkle.domain.util.HibernateUtils.unwrap;

import blog.cirkle.domain.entity.participant.Relation;
import blog.cirkle.domain.entity.participant.RelationRequest;
import blog.cirkle.domain.entity.participant.User;
import blog.cirkle.domain.model.UserDto;
import blog.cirkle.domain.model.response.RelationDto;
import blog.cirkle.domain.model.response.RelationRequestDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

	public UserDto toUserDto(User user) {
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setSlug(user.getSlug());
		userDto.setFirstName(user.getFirstName());
		userDto.setLastName(user.getLastName());
		userDto.setAvatarUrl(user.getAvatarUrl());
		return userDto;
	}

	public RelationDto toRelationDto(Relation rel) {
		User related = unwrap(rel.getRelated()).asUser();
		return RelationDto.builder().user(toUserDto(related)).since(rel.getUpdatedAt().getEpochSecond())
				.type(rel.getType()).build();
	}

	public RelationRequestDto toRelationRequestDto(RelationRequest relationRequest) {
		User initiator = unwrap(relationRequest.getInitiator()).asUser();
		return RelationRequestDto.builder().id(relationRequest.getId()).user(toUserDto(initiator))
				.since(relationRequest.getCreatedAt().getEpochSecond()).type(relationRequest.getType()).build();
	}
}
