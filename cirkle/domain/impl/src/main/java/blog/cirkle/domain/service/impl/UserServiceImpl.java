package blog.cirkle.domain.service.impl;

import blog.cirkle.domain.entity.participant.*;
import blog.cirkle.domain.exception.BadRequestException;
import blog.cirkle.domain.exception.NotAllowedException;
import blog.cirkle.domain.model.RegistrationResponse;
import blog.cirkle.domain.model.request.EmailValidationRequest;
import blog.cirkle.domain.model.request.RegistrationRequest;
import blog.cirkle.domain.model.request.UpdateUserRequest;
import blog.cirkle.domain.repository.participant.EmailValidationRepository;
import blog.cirkle.domain.repository.participant.UserRepository;
import blog.cirkle.domain.security.BlogUserDetails;
import blog.cirkle.domain.security.UserContextHolder;
import blog.cirkle.domain.service.UserService;
import blog.cirkle.domain.util.UUIDUtils;
import blog.cirkle.domain.utils.SlugUtils;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final EmailValidationRepository emailValidationRepository;

	@Override
	public void createDefaultUsers() {
		if (userRepository.count() == 1) {
			User defaultUser = User.builder().firstName("Oksana").lastName("Zarowska")
					.email("oksana.zarowska@cirkle.blog").passwordHash(passwordEncoder.encode("admin"))
					.avatarUrl(
							"https://cdn.cloudflare.steamstatic.com/steamcommunity/public/images/avatars/58/5839e66bf8adfec277abe6658039bc4d3947d08f.jpg")
					.role(User.UserRole.ADMIN).build();
			save(defaultUser);
		}
	}

	@Override
	public Page<User> findAll(Pageable pageable) {
		return userRepository.findByRoleNot(User.UserRole.SYSTEM, pageable);
	}

	@Override
	public Optional<User> findBySlug(String slug) {
		return userRepository.findByRoleNotAndSlug(User.UserRole.SYSTEM, slug);
	}

	@Override
	public Optional<User> findById(UUID id) {
		return userRepository.findByRoleNotAndId(User.UserRole.SYSTEM, id);

	}

	@Override
	public Optional<User> updateUser(UUID userId, UpdateUserRequest request) {
		BlogUserDetails currentUser = UserContextHolder.getCurrentUser().get();
		if (currentUser.getId() != userId || currentUser.isAdmin()) {
			return findById(userId).map(user -> {
				ifNotNull(request.getFirstName(), user::setFirstName);
				ifNotNull(request.getLastName(), user::setLastName);
				ifNotNull(user.getAvatarUrl(), user::setAvatarUrl);
				ifNotNull(request.getSlug(), user::setSlug);
				return user;
			});
		} else {
			throw new NotAllowedException("You cant update other users");
		}
	}

	@Override
	public RegistrationResponse register(RegistrationRequest request) {
		String avatar = "https://i.pravatar.cc/150?u=" + request.getEmail().hashCode();
		User user = User.builder().role(User.UserRole.USER).firstName(request.getFirstName())
				.lastName(request.getLastName()).avatarUrl(avatar).email(request.getEmail().toLowerCase())
				.passwordHash("-1").build();
		try {
			User saved = save(user);
			userRepository.flush();
			EmailValidation validation = emailValidationRepository.save(new EmailValidation(saved));
			return new RegistrationResponse(saved, UUIDUtils.uuidsToBase64(validation.getId(), validation.getCode()));
		} catch (DataIntegrityViolationException e) {
			throw new BadRequestException("User with such email already exists");
		}

	}

	@Override
	public User validateEmail(EmailValidationRequest request) {
		UUID[] keys = UUIDUtils.base64ToUUIDs(request.getValidationToken());
		return emailValidationRepository.findByIdAndCode(keys[0], keys[1]).map(reg -> {
			emailValidationRepository.deleteById(reg.getId());
			User user = reg.getUserRef();
			user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
			user.setEmailValidated(true);
			userRepository.flush();
			return user;
		}).orElseThrow(() -> new BadCredentialsException("Invalid validation token"));
	}

	private <T> void ifNotNull(T value, Consumer<T> action) {
		if (value != null) {
			action.accept(value);
		}
	}

	@Override
	public Optional<User> getFindById(UUID id) {
		return userRepository.findById(id);

	}

	private User save(User user) {
		user.setSlug(SlugUtils.slugify(user.getName(), slug -> !userRepository.existsBySlug(slug)));
		return userRepository.save(user);
	}
}
