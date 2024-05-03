package blog.cirkle.domain.impl;

import blog.cirkle.domain.entity.participant.User;
import blog.cirkle.domain.exception.ResourceNotFoundException;
import blog.cirkle.domain.facade.UserFacade;
import blog.cirkle.domain.model.RegistrationResponse;
import blog.cirkle.domain.model.request.RegistrationRequest;
import blog.cirkle.domain.model.request.UpdateUserRequest;
import blog.cirkle.domain.model.response.RegistrationResponseDto;
import blog.cirkle.domain.model.response.UserDto;
import blog.cirkle.domain.security.UserContextHolder;
import blog.cirkle.domain.service.UserService;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFacadeImpl implements UserFacade {
	private final UserService userService;

	@Override
	public Page<UserDto> findAll(Pageable pageable) {
		return userService.findAll(pageable).map(this::toUserDto);
	}

	@Override
	public UserDto findBySlug(String slug) {
		return userService.findBySlug(slug).map(this::toUserDto)
				.orElseThrow(() -> new ResourceNotFoundException("User", Map.of("slug", slug)));
	}

	@Override
	public UserDto findById(UUID id) {
		return userService.findById(id).map(this::toUserDto)
				.orElseThrow(() -> new ResourceNotFoundException("User", Map.of("id", id)));
	}

	@Override
	public UserDto getCurrentUser() {
		return findById(UserContextHolder.getCurrentUser().get().getId());
	}

	@Override
	public UserDto updateById(UUID id, UpdateUserRequest request) {
		return userService.updateUser(id, request).map(this::toUserDto)
				.orElseThrow(() -> new ResourceNotFoundException("User", Map.of("id", id)));
	}

	@Override
	public RegistrationResponseDto register(RegistrationRequest request) {
		RegistrationResponse response = userService.register(request);
		return new RegistrationResponseDto(toUserDto(response.getUser()), response.getEmailUrl());
	}

	private UserDto toUserDto(User user) {
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setSlug(user.getSlug().getSlug());
		userDto.setFirstName(user.getFirstName());
		userDto.setLastName(user.getLastName());
		userDto.setAvatarUrl(user.getAvatarUrl());
		return userDto;
	}
}
