package blog.cirkle.repository.participant;

import blog.cirkle.entity.participant.EmailValidation;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailValidationRepository extends JpaRepository<EmailValidation, UUID> {
	Optional<EmailValidation> findByIdAndCode(UUID id, UUID code);
}