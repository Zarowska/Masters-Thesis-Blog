package blog.cirkle.app.service.impl;

import blog.cirkle.app.api.rest.model.ResetPasswordDto;
import blog.cirkle.app.api.rest.model.UserProfileDto;
import blog.cirkle.app.api.rest.model.request.CreateUserDto;
import blog.cirkle.app.exception.BadRequestException;
import blog.cirkle.app.exception.NotFoundException;
import blog.cirkle.app.model.entity.User;
import blog.cirkle.app.repository.PasswordChangeRequestRepository;
import blog.cirkle.app.repository.UserRepository;
import blog.cirkle.app.service.ImageService;
import blog.cirkle.app.service.UserService;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordChangeRequestRepository passwordChangeRequestRepository;
	private final ImageService imageService;
	private final PasswordEncoder passwordEncoder;

	@Override
	public User createUser(CreateUserDto request) {
		if (userRepository.existsByEmail(request.getEmail())) {
			throw new BadRequestException("Email address already in use");
		}
		User user = new User(request.getEmail());
		user.getProfile().setName(request.getFullName());
		user.getProfile().setHandle(generateUniqueHandle(request.getFullName()));
		return userRepository.save(user);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmailIgnoreCase(email);
	}

	@Override
	public Optional<User> findByIdOptional(UUID id) {
		return userRepository.findById(id);
	}

	@Override
	public User findById(UUID id) {
		return userRepository.findById(id).orElseThrow(() -> NotFoundException.resource("User", Map.of("id", id)));
	}

	@Override
	public Page<User> findAll(String query, Pageable pageable) {
		if (query == null || query.isEmpty()) {
			return userRepository.findAll(pageable);
		} else {
			String searchQuery = "%" + query + "%";
			return userRepository.findByProfile_NameLikeIgnoreCaseOrProfile_BioLikeIgnoreCase(searchQuery, searchQuery,
					pageable);
		}

	}

	@Override
	public User updateProfile(UUID userId, UserProfileDto userProfile) {
		return null;
	}

	@Override
	@Transactional
	public void updatePassword(User user, ResetPasswordDto request) {
		String updatedPassword = passwordEncoder.encode(request.getPassword());
		userRepository.updatePassword(user.getId(), updatedPassword);
		user.setPasswordHash(updatedPassword);
		userRepository.flush();
	}

	@Override
	public Page<User> listFollowersByUserId(UUID id, Pageable pageable) {
		User user = findById(id);
		return pageUsers(pageable, user.getFollowers());
	}

	@Override
	public Page<User> listFriendsByUserId(UUID id, Pageable pageable) {
		User user = findById(id);
		return pageUsers(pageable, user.getFriends());
	}

	@Override
	public Long totalUsers() {
		return userRepository.count();
	}

	private static Page<User> pageUsers(Pageable pageable, Set<User> userSet) {
		int start = (int) pageable.getOffset();
		int end = Math.min((start + pageable.getPageSize()), userSet.size());

		List<User> followersList = userSet.stream().collect(Collectors.toList()).subList(start, end);

		return new PageImpl<>(followersList, pageable, userSet.size());
	}

	private String generateUniqueHandle(String fullName) {
		String base = generateHandle(fullName);
		String handle = base;
		int counter = 0;
		while (userRepository.existsByFollowers_Profile_Handle(handle)) {
			handle = base + (++counter);
		}
		return handle;
	}

	private String generateHandle(String fullName) {
		String filteredName = fullName.replaceAll("[^a-zA-Z]", "").toLowerCase();

		if (filteredName.length() < 3) {
			return "user";
		} else {
			String firstName = filteredName.split(" ")[0];
			String lastName = filteredName.split(" ")[filteredName.split(" ").length - 1];

			if (firstName.length() >= 3) {
				return firstName;
			} else {
				return firstName + lastName.substring(0, Math.min(3 - firstName.length(), lastName.length()));
			}
		}
	}
}
