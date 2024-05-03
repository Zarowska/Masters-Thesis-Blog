package blog.cirkle.domain.facade;

import blog.cirkle.domain.model.request.RegistrationRequest;
import blog.cirkle.domain.model.request.UpdateUserRequest;
import blog.cirkle.domain.model.response.RegistrationResponseDto;
import blog.cirkle.domain.model.response.UserDto;
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
}
