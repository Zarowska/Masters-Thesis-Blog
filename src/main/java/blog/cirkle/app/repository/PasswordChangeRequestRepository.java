package blog.cirkle.app.repository;

import blog.cirkle.app.model.entity.PasswordChangeRequest;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordChangeRequestRepository extends JpaRepository<PasswordChangeRequest, UUID> {
	Optional<PasswordChangeRequest> findByIdAndRequestId(UUID id, UUID requestId);
}