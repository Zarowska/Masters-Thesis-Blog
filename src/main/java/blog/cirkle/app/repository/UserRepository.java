package blog.cirkle.app.repository;

import blog.cirkle.app.model.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, UUID> {
	boolean existsByFollowers_Email(String email);

	boolean existsByEmail(String email);

	boolean existsByFollowers_Profile_Handle(String handle);

	@Modifying
	@Query("UPDATE User u SET u.passwordHash = :passwordHash where u.id = :id")
	void updatePassword(@Param("id") UUID id, @Param("passwordHash") String password);

	Optional<User> findByFollowers_Email(String email);

	Optional<User> findByEmailIgnoreCase(String email);

	Page<User> findByProfile_NameLikeIgnoreCaseOrProfile_BioLikeIgnoreCase(String name, String bio, Pageable pageable);
}