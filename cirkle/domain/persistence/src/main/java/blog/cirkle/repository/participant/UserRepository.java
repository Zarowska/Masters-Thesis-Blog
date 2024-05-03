package blog.cirkle.repository.participant;

import blog.cirkle.entity.participant.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {
	Optional<User> findByEmailAndRoleNot(String email, User.UserRole notRole);

	Page<User> findByRoleNot(User.UserRole role, Pageable pageable);

	Optional<User> findByRoleNotAndSlug_slug(User.UserRole userRole, String slug);

	Optional<User> findByRoleNotAndId(User.UserRole userRole, UUID id);
}