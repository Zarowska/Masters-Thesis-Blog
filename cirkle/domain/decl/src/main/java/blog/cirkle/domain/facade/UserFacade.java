package blog.cirkle.domain.facade;

import blog.cirkle.domain.model.UserDto;
import blog.cirkle.domain.model.request.RegistrationRequest;
import blog.cirkle.domain.model.request.UpdateUserRequest;
import blog.cirkle.domain.model.response.RegistrationResponseDto;
import blog.cirkle.domain.model.response.RelationDto;
import blog.cirkle.domain.model.response.RelationRequestDto;
import blog.cirkle.domain.model.response.RelationType;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface UserFacade {
	Page<UserDto> findAll(Pageable pageable);

	UserDto findBySlug(String slug);

	UserDto findById(UUID id);

	UserDto getCurrentUser();

	@Transactional
	UserDto updateById(UUID userId, UpdateUserRequest request);

	@Transactional
	RegistrationResponseDto register(RegistrationRequest request);

	Page<RelationDto> findRelationsByType(UUID userId, Set<RelationType> filter, Pageable pageable);

	@Transactional
	void createRelation(UUID userId, RelationType relationType);

	@Transactional
	void removeRelation(UUID userId, RelationType relationType);

	Page<RelationRequestDto> listUserRelationRequests(Pageable pageable);

	@Transactional
	void acceptUserRelationRequest(UUID requestId);

	@Transactional
	void rejectUserRelationRequest(UUID requestId);
}
