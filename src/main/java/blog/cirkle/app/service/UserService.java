package blog.cirkle.app.service;

import blog.cirkle.app.api.rest.model.ResetPasswordDto;
import blog.cirkle.app.api.rest.model.UserProfileDto;
import blog.cirkle.app.api.rest.model.request.CreateUserDto;
import blog.cirkle.app.model.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

	User createUser(CreateUserDto request);

	Optional<User> findByEmail(String email);

	Optional<User> findByIdOptional(UUID id);

	User findById(UUID id);

	Page<User> findAll(String query, Pageable pageable);

	User updateProfile(UUID userId, UserProfileDto userProfile);

	void updatePassword(User user, ResetPasswordDto request);

	Page<User> listFollowersByUserId(UUID id, Pageable pageable);

	Page<User> listFriendsByUserId(UUID id, Pageable pageable);

	Long totalUsers();
}
