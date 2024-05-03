package blog.cirkle.domain.service;

import blog.cirkle.domain.entity.participant.User;
import blog.cirkle.domain.model.RegistrationResponse;
import blog.cirkle.domain.model.request.EmailValidationRequest;
import blog.cirkle.domain.model.request.RegistrationRequest;
import blog.cirkle.domain.model.request.UpdateUserRequest;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

	Optional<User> getFindById(UUID id);

	void createDefaultUsers();

	Page<User> findAll(Pageable pageable);

	Optional<User> findBySlug(String slug);

	Optional<User> findById(UUID id);

	Optional<User> updateUser(UUID userId, UpdateUserRequest request);

	RegistrationResponse register(RegistrationRequest request);

	User validateEmail(EmailValidationRequest request);
}
